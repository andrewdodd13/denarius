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

/**
 * Formats a currency value using the currency field and 2 decimal places.
 * @param value
 * @returns {String}
 */
function formatCurrency(value) {
	return currency + Number(value()).toFixed(2);
}

function describeDay(day, month, year, data) {
	var dayTotal = 0,
		dayAccounts = [];
	
	for(var account in data) {
		var entry = 0,
			dateIndex = year + '-' + datePad(month + 1) + '-' + datePad(day);
		if(dateIndex in data[account]['entries']) {
			entry = data[account]['entries'][dateIndex];
		}
		
		dayTotal += entry;
		
		dayAccounts.push({
			accountId   : data[account]['accountId'],
			accountName : data[account]['accountName'],
			entry       : {
				valueOnDate : entry
			}
		});
	}	
	
	return {
		header: ordi(day),
		accounts: dayAccounts,
		total: {
			totalValue : dayTotal
		}
	};
}

// TODO: Register an AJAX Error callback in case of anything going wrong

function DenariusViewModel() {
    var self = this;

    self.accounts = ko.observableArray([]);

}

/**
 * Does a complete fetch from the server. Call this when changing month.
 * 
 */
function reloadData() {
    $('#month-title').text(monthNames[month] + ' ' + year);

    // Fetch and parse the account data
    $.get('account/list-values/' + year + '/' + (month + 1), null, function(data, status) {
        // Calculate the required dates (first, last and middle weeks)
        var firstDay = (startDay - new Date(year, month, 1).getDay() + 7) % 7 + 1;
        if (firstDay <= 1)
            firstDay += 7;
        var daysInMonth = new Date(year, month + 1, 0).getDate();

        // Describe the first day
        var dayDescription = describeDay(1, month, year, data);
        accountsModel['headers'] = [ dayDescription['header'] ];
        accountsModel['totals'] = [ dayDescription['total'] ];
        for (var i = 0; i < dayDescription['accounts'].length; i++) {
        	accountsModel['accounts'] = [];
        	accountsModel['accounts'][i] = dayDescription['accounts'][i];
        	accountsModel['accounts'][i]['entries'] = [ accountsModel['accounts'][i]['entry'] ];
        	delete accountsModel['accounts'][i]['entry'];
        }
        
        // Describe the intermediate days
        for ( var i = firstDay; i < daysInMonth; i += 7) {
        	dayDescription = describeDay(i, month, year, data);
        	accountsModel['headers'].push(dayDescription['header']);
            accountsModel['totals'].push(dayDescription['total']);
            for (var accountI = 0; accountI < dayDescription['accounts'].length; accountI++) {
            	accountsModel['accounts'][accountI]['entries'].push(dayDescription['accounts'][accountI]['entry']);
            }
        }
        
        // Describe the last day
    	dayDescription = describeDay(daysInMonth, month, year, data);
        accountsModel['headers'].push(dayDescription['header']);
        accountsModel['totals'].push(dayDescription['total']);
        for (var i = 0; i < dayDescription['accounts'].length; i++) {
        	accountsModel['accounts'][i]['entries'].push(dayDescription['accounts'][i]['entry']);
        }
                
        accountsModel = ko.mapping.fromJS(accountsModel);
        ko.applyBindings(accountsModel);

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