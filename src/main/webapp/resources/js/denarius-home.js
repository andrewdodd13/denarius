var month, year;

var monthNames = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

var startDay = 1; // Monday

var currency = '£'; // It's nice having a currency that lets everyone know where
                    // you're from :)

var accountsModel = {
    'totals' : {},
    'accounts' : {}
}, statisticsModel = {

}, transactionsModel = {

};

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
 * @param n
 *            the number to pad
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
        accountsModel['accounts'] = data;
        accountsModel['totals'] = {
            '1' : 0
        };

        var accountsTable = $('#accounts-table');
        accountsTable.empty();

        // Calculate the required dates (first, last and middle weeks)
        var firstDay = (startDay - new Date(year, month, 1).getDay() + 7) % 7 + 1;
        if (firstDay <= 1)
            firstDay += 7;
        var daysInMonth = new Date(year, month + 1, 0).getDate();

        var calendarDates = [ 1 ];
        for ( var i = firstDay; i < daysInMonth; i += 7) {
            calendarDates.push(i);
            accountsModel['totals'][i] = 0;
        }
        calendarDates.push(daysInMonth);
        accountsModel['totals'][daysInMonth] = 0;

        // Draw header row
        var headers = "";
        for (i in calendarDates) {
            headers += "<th>" + ordi(calendarDates[i]) + "</th>";
        }

        accountsTable.append('<thead><tr><th>Account</th>' + headers + '</tr></thead>');

        // Draw content
        var accountsTableTotals = $('<tfoot />');
        accountsTable.append(accountsTableTotals);

        var totalRow = $('<tr />');
        accountsTableTotals.append(totalRow);

        var accountsTableBody = $('<tbody />');
        accountsTable.append(accountsTableBody);
        for ( var account in data) {
            var row = $('<tr />');
            row.append('<td>' + data[account].accountName + '</tr>');
            for ( var date in calendarDates) {
                var datestr = data[account].entries[year + '-' + datePad(month + 1) + '-' + datePad(calendarDates[date])];
                var cell;
                if (datestr != null) {
                    cell = $('<td>' + currency + datestr.toFixed(2) + '</td>');
                    // Add the value to the totals row
                    accountsModel['totals'][calendarDates[date]] += datestr;
                } else {
                    cell = $('<td>N/A</td>');
                }

                // If this date is before today then bind the ability to change
                if (new Date(year, month, calendarDates[date]) <= new Date()) {
                    cell.addClass('account-cell-past');
                    cell.click(function(e) {
                        var target = $(e.target);
                        target.empty();
                        target.append('<input type="text" class="input-mini" />');
                        target.unbind('click');
                    });
                }
                // Otherwise, colour the cell grey
                else {
                    cell.addClass('account-cell-future');
                }
                
                row.append(cell);
            }
            accountsTable.append(row);
        }

        totalRow.append('<td>Totals</td>');
        for ( var date in calendarDates) {
            var datestr = accountsModel['totals'][calendarDates[date]];
            if (datestr != null) {
                totalRow.append('<td>' + currency + datestr.toFixed(2) + '</td>');
            } else {
                totalRow.append('<td>N/A</td>');
            }
        }
        
        console.log(accountsModel['totals']);
    });

    // Fetch the statistics
    $.get('statistics/get/' + year + '/' + (month + 1), null, function(data, status) {

    });

    // Fetch the transactions
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