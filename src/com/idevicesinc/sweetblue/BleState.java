package com.idevicesinc.sweetblue;

import android.bluetooth.BluetoothAdapter;

import com.idevicesinc.sweetblue.utils.State;

/**
 * An enumeration of the various states that a {@link BleManager} can be in.
 * The manager can be in multiple states simultaneously.
 * 
 * @see BleManager.StateListener
 * @see BleManager.NativeStateListener
 * @see BleManager#is(BleState)
 * @see BleManager#getStateMask()
 */
public enum BleState implements State
{
	/**
	 * Analogous to {@link BluetoothAdapter#STATE_OFF}.
	 */
	OFF					(BluetoothAdapter.STATE_OFF),
	
	/**
	 * Analogous to {@link BluetoothAdapter#STATE_TURNING_ON}.
	 */
	TURNING_ON			(BluetoothAdapter.STATE_TURNING_ON),
	
	/**
	 * Analogous to {@link BluetoothAdapter#STATE_ON}.
	 */
	ON					(BluetoothAdapter.STATE_ON),
	
	/**
	 * Analogous to {@link BluetoothAdapter#STATE_TURNING_OFF}.
	 */
	TURNING_OFF			(BluetoothAdapter.STATE_TURNING_OFF),
	
	/**
	 * This is the state that {@link BleManager} is in after calling {@link BleManager#startScan()} or related overloads.
	 * 
	 * @see BleManager#startScan()
	 */
	SCANNING,
	
	/**
	 * This is the state that {@link BleManager} is in after calling {@link BleManager#dropTacticalNuke()}.
	 */
	NUKING;
	
	private final int m_nativeCode;
	
	private BleState()
	{
		m_nativeCode = 0;
	}
	
	private BleState(int nativeCode)
	{
		m_nativeCode = nativeCode;
	}
	
	/**
	 * Returns the analogous native code, if applicable. For example {@link BluetoothAdapter#STATE_OFF},
	 * {@link BluetoothAdapter#STATE_ON}, etc. {@link #NUKING} and {@link #SCANNING} do not have a native
	 * code equivalent and will return 0.
	 */
	public int getNativeCode()
	{
		return m_nativeCode;
	}
	
	static BleState get(int nativeCode)
	{
		for( int i = 0; i < values().length; i++ )
		{
			if( values()[i].getNativeCode() == nativeCode )
			{
				return values()[i];
			}
		}
		
		return null;
	}

	@Override public int bit()
	{
		return 0x1 << ordinal();
	}
	
	@Override public boolean overlaps(int mask)
	{
		return (bit() & mask) != 0x0;
	}
	
	@Override public boolean wasEntered(int oldStateBits, int newStateBits)
	{
		return !this.overlaps(oldStateBits) && this.overlaps(newStateBits);
	}

	@Override public boolean wasExited(int oldStateBits, int newStateBits)
	{
		return this.overlaps(oldStateBits) && !this.overlaps(newStateBits);
	}
}
