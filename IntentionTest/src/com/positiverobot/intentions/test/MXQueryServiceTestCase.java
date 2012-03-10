package com.positiverobot.intentions.test;

import android.content.Intent;
import android.os.IBinder;
import android.test.ServiceTestCase;
import ch.ethz.mxquery.android.MXQueryService;
import ch.ethz.mxquery.android.MXQueryService.MXQueryServiceBinder;
import ch.ethz.mxquery.android.XQueryListener;

/**
 * Check out assumptions for the MXQuery library
 * 
 * @author byeo
 * 
 */
public class MXQueryServiceTestCase extends ServiceTestCase<MXQueryService> {

	private boolean _mxqInit = false;

	public MXQueryServiceTestCase() {
		super(MXQueryService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!_mxqInit) {
			// MXQuery.init(getContext());
			_mxqInit = true;
		}
	}

	public void testPackageQuery() {
		XQueryListener queryListener = new XQueryListener() {

			@Override
			public void queryResult(String result) {

			}
		};

		Intent wake = new Intent(getContext(), MXQueryService.class);
		MXQueryServiceBinder binder =
				 (MXQueryServiceBinder) bindService(wake);
		assertNotNull("Expecting a binding",binder);
		
		MXQueryService service = getService();
		service.runQuery("", queryListener);
	}
}
