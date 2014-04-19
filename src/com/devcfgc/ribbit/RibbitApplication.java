package com.devcfgc.ribbit;

import android.app.Application;

import com.parse.Parse;

/**
 * 
 * @author devcfgc This is class is going to replace the apllication class as an
 *         entry point We are going to define initials setups for our app
 * 
 */

public class RibbitApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "1RBx5kPNmCbOdRA1jHnjk9KKY4kIcp31N9jebN23",
				"stun96okA232XOSVYxIZVuveuzDQdc74cmC3LaPZ");
	}

}
