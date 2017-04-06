package com.ledo.sdxl.codec;

/**
 * UTF8的实现
 * 
 * @author CaiJiahe
 */
public class UTF8 implements Codec {

	public static final long SIX_BIT_MASK = 0x3F;
	public static final long[] LENGHT_UPLIMIT = {1L << 7, 1L << 11, 1L << 16, 1L << 21, 1L << 26, 1L << 31}; 
	
	public long encode(long unicode) {
		if (unicode < 0) {
			return -1;
		}
		
		// 单字节可以使用ascii编码
		if (unicode < LENGHT_UPLIMIT[0]) {
			return unicode;
		}
		
		/**
		 * 编码的过程就是先找到unicode的范围，映射字节长度，确定下首字节的高位，字节长度+0。
		 * 然后对unicode以6bit作为分割单位，切分好，从低位向高位拼接 10 + 6bit，
		 * 首字节的剩余部分补0。
		 */
		for (int i = 1; i < LENGHT_UPLIMIT.length; i++) {
			if (unicode < LENGHT_UPLIMIT[i]) {
				// 切分6bit
				byte[] bytes = new byte[i];
				long mask = SIX_BIT_MASK;
				for (int j = 0; j < i; j++) {
					bytes[j] += (unicode & mask);
					unicode >>>= 6;
				}
				
				// 构造首字节
				long result = 1L;
				while (i > 0) {
					result <<= 1;
					result += 1;
					i--;
				}
				result <<= 8 - (bytes.length + 1);
				result += (byte)(unicode & 0xFF);
				
				// 构造剩余字节
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
		
		// 寻找首字节
		long result = 0, mask = 0xFF0000000000L;
		int curByte;
		int shift = 40;
		do {
			curByte = (int) ((utfcode & mask) >> shift);
			mask >>= 8;
			shift -= 8;
		} while(curByte == 0 && mask > 0);
		
		// 如果是单字节直接返回utfcode
		if (mask <= 0) {
			return utfcode;
		}
		
		
		/**
		 * fromBitPtr是指向utfcode的bit数组指针，toBitPtr是指向unicode的bit数组指针。
		 * 忽略除首位字节外的其他字节的前缀0x80。
		 */
		int totalBytes = shift/8 + 2;
		
		long shiftBits = (totalBytes - 1)*6 + (7 - totalBytes);
		long toBitPtr = 1L << shiftBits;
		
		shiftBits += (totalBytes - 1)*2;
		long fromBitPtr = 1L << shiftBits;
		
		for (; shiftBits >= 0; fromBitPtr >>= 1, shiftBits--) {
			if (shiftBits % 8 < 6) {
				if ((fromBitPtr & utfcode) != 0) {
					result |= toBitPtr;
				}
				toBitPtr >>= 1;
			}
		}
		
		return result;
	}
	
}
