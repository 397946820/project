document.write("<script src='https://www.paypalobjects.com/api/checkout.js'></script>");
$(function(){
	
	/*paypal.Button.render({
	      env: 'sandbox', // Or 'sandbox',
	      client:{
	    	  sandbox:'AZDxjDScFpQtjWTOUtWKbyN_bDt4OgqaF4eYXlewfBP4-8aqX3PiV8e1GWU6liB2CUXlkA59kJXE7M6R',
	    	  production:'AQ45-O2pVl6GASJVnhY0ARJsBdLKv40q3ir4kamiXnmeVhLNtvUl9V9mBTS--3qmmAC9mnq83QjEqiiU'
	      },
	      commit: true, // Show a 'Pay Now' button

	      style: {
	        color: 'gold',
	        size: 'small'
	      },

	      payment: function(data, actions) {
	         
	         * Set up the payment here 
	         
	    	  return actions.payment.create({
	                payment: {
	                    transactions: [
	                        {
	                            amount: { total: '1.00', currency: 'USD' }
	                        }
	                    ]
	                }
	            });
	      },

	      onAuthorize: function(data, actions) {
	         
	         * Execute the payment here 
	         
	    	  return actions.payment.execute().then(function(payment) {

	                // The payment is complete!
	                // You can now show a confirmation message to the customer
	            });
	      },

	      onCancel: function(data, actions) {
	         
	         * Buyer cancelled the payment 
	         
	      },

	      onError: function(err) {
	         
	         * An error occurred during the transaction 
	         
	      }
	    }, '#paypal-button');*/
paypal.Button.render({

    env: 'sandbox', // sandbox | production

    // PayPal Client IDs - replace with your own
    // Create a PayPal app: https://developer.paypal.com/developer/applications/create
    client: {
        sandbox:    'AZDxjDScFpQtjWTOUtWKbyN_bDt4OgqaF4eYXlewfBP4-8aqX3PiV8e1GWU6liB2CUXlkA59kJXE7M6R',
        production: 'Ac734lyiM7Jn2cAYEcv4ycJHAdVL7NQSG1r3cgtfmLo4yMirXGFlnoNzfp-2M_OTLYCva2dXiAf068BM'
    },

    // Show the buyer a 'Pay Now' button in the checkout flow
    commit: true,

    // payment() is called when the button is clicked
    payment: function(data, actions) {

        // Make a call to the REST api to create the payment
        return actions.payment.create({
            payment: {
                transactions: [
                    {
                        amount: { total: '2.00', currency: 'USD' }
                    }
                ]
            }
        });
    },

    // onAuthorize() is called when the buyer approves the payment
    onAuthorize: function(data, actions) {

        // Make a call to the REST api to execute the payment
        return actions.payment.execute().then(function() {
            window.alert('Payment Complete!');
        });
    }

}, '#paypal-button-container');

})