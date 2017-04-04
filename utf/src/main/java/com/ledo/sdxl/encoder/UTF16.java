package com.ledo.sdxl.encoder;

public class UTF16 implements Codec {

	public long encode(long unicode) {
		if (unicode < 0 || unicode > 17*(1 << 16)) {
			return -1;
		}
		
		if (unicode < (1 << 16)) {
			return unicode;
		}
		
		long highMask = 0xFFC00L, lowMask = 0x3FFL;
		return ((0xD800L|((unicode&highMask)>>10L))<<16L) + (0xDC00L|(unicode&lowMask));
	}

	public long decode(long code) {
		// TODO Auto-generated method stub
		return 0;
	}

}
