package com.ledo.sdxl;

public class Utf8 implements Encoder {

	final static long[] LENGHT_UPLIMIT = {1 << 7, 1 << 11, 1 << 16, 1 << 21, 1 << 26, 1 << 31}; 
	
	public long encode(long unicode) {
		for (int i = 0; i < LENGHT_UPLIMIT.length; i++) {
			if (unicode < LENGHT_UPLIMIT[i]) {
				if (i == 0) {
					return unicode;
				} 

				byte[] bytes = new byte[i];
				long mask = 0x3F;
				for (int j = 0; j < i; j++) {
					bytes[j] += (unicode&mask);
					unicode >>>= 6;
				}
//				bytes[i] = (byte)(unicode&0xFF);
				long result = 0L;
				while (i >= 0) {
					result <<= 1;
					result += 1;
					i--;
				}
				result <<= 1;
				result += (byte)(unicode&0xFF);
				for (int j = bytes.length - 1; j >= 0; j++) {
					result <<= 8;
					result += bytes[j];
					result += 128;
				}
				return result;
			}
		}
		return -1L;
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Utf8().encode(0xABCD));
	}
}
