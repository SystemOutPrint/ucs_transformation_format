package com.ledo.sdxl.codec;

/**
 * UTF8µÄÊµÏÖ
 * 
 * @author CaiJiahe
 */
public class UTF8 implements Codec {

	final static long[] LENGHT_UPLIMIT = {1L << 7, 1L << 11, 1L << 16, 1L << 21, 1L << 26, 1L << 31}; 
	
	public long encode(long unicode) {
		if (unicode < 0) {
			return -1;
		}
		
		for (int i = 0; i < LENGHT_UPLIMIT.length; i++) {
			if (unicode < LENGHT_UPLIMIT[i]) {
				if (i == 0) {
					return unicode;
				} 

				byte[] bytes = new byte[i];
				long mask = 0x3F;
				for (int j = 0; j < i; j++) {
					bytes[j] += (unicode & mask);
					unicode >>>= 6;
				}
				long result = 1L;
				while (i > 0) {
					result <<= 1;
					result += 1;
					i--;
				}
				result <<= 7 - bytes.length;
				result += (byte)(unicode & 0xFF);
				for (int j = bytes.length - 1; j >= 0; j--) {
					result <<= 8;
					result += bytes[j];
					result += 128;
				}
				return result;
			}
		}
		return -1;
	}
	
	public long decode(long utfcode) {
		if (utfcode < 0 || utfcode > 0xFDFFFFFFFFFFL) {
			return -1;
		}
		
		long result = 0, mask = 0xFF0000000000L;
		int curByte;
		int shift = 40;
		do {
			curByte = (int) ((utfcode & mask) >> shift);
			mask >>= 8;
			shift -= 8;
		} while(curByte == 0 && mask > 0);
		
		if (mask <= 0) {
			return utfcode;
		}
		
		int totalBytes = shift/8 + 2;
		
		long shiftBits = (totalBytes - 1)*6 + (7 - totalBytes);
		long bitMask1 = 1L << shiftBits;
		
		shiftBits += (totalBytes - 1)*2;
		long bitMask2 = 1L << shiftBits;
		
		for (; shiftBits >= 0; bitMask2 >>= 1, shiftBits--) {
			if (shiftBits % 8 >= 6) {
				continue;
			}
			if ((bitMask2 & utfcode) != 0) {
				result |= bitMask1;
			}
			bitMask1 >>= 1;
		}
		
		return result;
	}
	
}
