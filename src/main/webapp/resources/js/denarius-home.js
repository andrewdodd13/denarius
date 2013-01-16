var month, year;

var monthNames = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

var startDay = 1; // Monday

var currency = '£'; 

var accounts;

function AccountModel() {
	var self     = this;
	this.accounts = ko.observableArray();
	this.totals   = ko.observableArray();
	this.headers  = [];
	this.days     = [];
	
	/**
	 * Adds a new account with the given ID (where ID is the Server-Side ID) and name. 
	 */
	this.addAccount = function (accountId, accountName) {
		self.accounts.push({
			accountId:   accountId,
			accountName: accountName,
			entries:     ko.observableArray()
		});
	};
	
	/**
	 * Adds a value to the account. The index is the index of the account in the 
	 * accounts array. Values must be added in the order they are to be displayed.
	 */
	this.addAccountValue = function (accountIndex, value) {
		self.accounts()[accountIndex].entries.push({
			valueOnDate:	ko.observable(value)
		});
	};
	
	/**
	 * Adds a new day to the model. This initialises both the header value and
	 * totals watcher.
	 */
	this.addDay = function(day) {
		var newIndex = self.days.length;
		self.days.push(day);
		self.headers.push(ordi(day));
		self.totals.push({
			valueTotal : ko.computed(function() {
				var total = 0;
				for(var i = 0; i < self.accounts().length; i++) {
					var toAdd = 0;
					if (day in self.accounts()[i].entries()) {
						toAdd += self.accounts()[i].entries()[newIndex].valueOnDate();
					}
					total += toAdd;
				}
				return total;
			})
		});
	};
}

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
 * Returns a date formatted as yyyy-mm-dd.
 * 
 * @param year
 * @param month
 * @param day
 * @returns {String}
 */
function dateFormat(year, month, day) {
	return year + "-" + datePad(month) + "-" + datePad(day);
}

/**
 * Formats a currency value using the currency field and 2 decimal places.
 * @param value
 * @returns {String}
 */
function formatCurrency(value) {
	return currency + Number(value()).toFixed(2);
}

/**
 * Does a complete fetch from the server. Call this when changing month.
 * 
 */
function reloadData() {
    $('#month-title').text(monthNames[month] + ' ' + year);

    // Fetch and parse the account data
    $.get('account/list-values/' + year + '/' + (month + 1), null, function(data, status) {
    	accounts = new AccountModel();
        
        // Calculate the required dates (first, last and middle weeks)
        var firstDay = (startDay - new Date(year, month, 1).getDay() + 7) % 7 + 1;
        if (firstDay <= 1) {
            firstDay += 7;
        }
        var daysInMonth = new Date(year, month + 1, 0).getDate();
        accounts.addDay(1);
        for ( var i = firstDay; i < daysInMonth; i += 7) {
        	accounts.addDay(i);
        }
        accounts.addDay(daysInMonth);
                
        // Add the accounts to the account model
        for (var i = 0; i < data.length; i++) {
        	accounts.addAccount(data[i].accountId, data[i].accountName);
        }
        
        for (var i = 0; i < accounts.days.length; i++) {
            for (var j = 0; j < data.length; j++) {
            	var dayIndex = dateFormat(year, month + 1, accounts.days[i]);
            	var entryValue = 0;
            	if(dayIndex in data[j].entries) {
            		entryValue = data[j].entries[dayIndex];
            	}
            	accounts.addAccountValue(j, entryValue);
            }
        }
        
        ko.applyBindings(accounts);
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
    
    $('#accounts-table').on('click', '.account-entry-value', function() {
    	$(this).hide();
    	var entryField = $(this).parent().find('.account-entry-field');
    	if(entryField.val() == '0') {
    		entryField.val('');
    	}
    	entryField.show();
    	entryField.focus();
    });
    
    $('#accounts-table').on('blur', '.account-entry-field', function() {
    	$(this).hide();
    	$(this).parent().find('.account-entry-value').show();
    	
    	// Trigger recalc
    });

    // Fire off a load event
    reloadData();
});