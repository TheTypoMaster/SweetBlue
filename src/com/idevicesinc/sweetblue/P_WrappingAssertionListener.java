package com.idevicesinc.sweetblue;

import android.os.Handler;

/**
 * 
 * 
 *
 */
class P_WrappingAssertionListener extends PA_CallbackWrapper implements BleManager.AssertListener
{
	private final BleManager.AssertListener m_listener;
	
	P_WrappingAssertionListener(BleManager.AssertListener listener, Handler handler, boolean postToMain)
	{
		super(handler, postToMain);
		
		m_listener = listener;
	}

	@Override public void onEvent(final AssertEvent info)
	{
		if( postToMain() )
		{
			m_handler.post(new Runnable()
			{
				@Override public void run()
				{
					m_listener.onEvent(info);
				}
			});
		}
		else
		{
			m_listener.onEvent(info);
		}
	}
}
