var month, year;

var monthNames = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

var startDay = 0; // Monday

var currency = '£'; // It's nice having a currency that lets everyone know where you're from :)

/**
 * Gets the ordinal for the given number assuming the number is a valid
 * Gregorian date.
 * 
 * @param n
 *            the number to get the ordinal for
 * @returns {String} th, st, nd, rd depending on the date
 * 
 */
function ordi(n) {
    var s = 'th';
    if (n == 1 || n == 21 || n == 31) {
        s = 'st';
    } else if (n == 2 || n == 22) {
        s = 'nd';
    } else if (n == 3 || n == 23) {
        s = 'rd';
    }
    return n + s;
}

/**
 * Zero pads a number for a date or month to two digits in order to conform to
 * ISO-8601. Only valid for 1-31.
 * 
 * @param n the number to pad
 * @returns
 */
function datePad(n) {
    if (n < 10) {
        return '0' + n;
    } else {
        return n;
    }
}

// TODO: Register an AJAX Error callback in case of anything going wrong

function DenariusViewModel() {
    var self = this;

    self.accounts = ko.observableArray([]);

}

ko.applyBindings(new DenariusViewModel());

/**
 * Does a complete fetch from the server. Call this when changing month.
 * 
 */
function reloadData() {
    $('#month-title').text(monthNames[month] + ' ' + year);

    // Fetch and parse the account data
    $.get('account/list-values/' + year + '/' + (month + 1), null, function(data, status) {
        var accountsTable = $('#accounts-table');
        accountsTable.empty();

        // Calculate the required dates (first, last and middle weeks)
        var firstDay = (startDay - new Date(year, month + 1, 1).getDay() + 7) % 7;
        if (firstDay <= 1)
            firstDay += 7;
        var daysInMonth = new Date(year, month + 1, 0).getDate();

        var calendarDates = [ 1 ];
        for ( var i = firstDay; i < daysInMonth; i += 7) {
            calendarDates.push(i);
        }
        calendarDates.push(daysInMonth);

        // Draw header row
        var headers = "";
        for (i in calendarDates) {
            headers += "<th>" + ordi(calendarDates[i]) + "</th>";
        }

        accountsTable.append('<thead><tr><th>Account</th>' + headers + '</tr></thead>');

        // Draw content
        var accountsTableBody = $('<tbody></tbody>');
        accountsTable.append(accountsTableBody);
        for ( var account in data) {
            var row = $('<tr />');
            row.append('<td>' + data[account].accountName + '</tr>');
            for ( var date in calendarDates) {
                var datestr = data[account].entries[year + '-' + datePad(month + 1) + '-' + datePad(calendarDates[date])];
                if (datestr) {
                    row.append('<td>' + currency + datestr.toFixed(2) + '</td>');
                } else {
                    row.append('<td>N/A</td>');
                }
            }
            accountsTable.append(row);
        }
        console.log(data);
    });
}

$(document).ready(function() {
    // Set up the current month and year
    month = -1;
    if (location.hash) {
        var parts = /#([0-9]{4})-([0-9]{1,2})/g.exec(location.hash);
        if (parts != null) {
            // Remember to subtract one
            month = parts[2] - 1;
            // Ensure validity
            if (month < 0 || month > 11) {
                month = -1;
            }
            year = parts[1];
        }
    }
    // If the month isn't set or is invalid, then use the current date
    if (month == -1) {
        var now = new Date();
        month = now.getMonth();
        year = now.getFullYear();
    }

    // Fire off a load event
    reloadData();
});