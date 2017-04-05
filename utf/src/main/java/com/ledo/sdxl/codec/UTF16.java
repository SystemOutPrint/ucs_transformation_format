package com.ledo.sdxl.codec;

/**
 *	UTF16��ʵ��
 * 
 * @author CaiJiahe
 */
public class UTF16 implements Codec {
	
	public static final long UNICODE_DWORD_START_POS = 0x10000L;
	public static final long UNICODE_DWORD_END_POS = 0x10FFFFL;
	
	/**
	 * unicodeΪutf16���뱣��0xD800��0xDFFF��
	 * 0xD800:1101 10 00 0000 0000
	 * 0xDC00:1101 11 00 0000 0000
	 * ������ħ���ĺ�10λ������λ������ܱ�ʾ20λ�����֡�
	 * utf16�ĸ���ƽ�����Ĵ�С����20λ��
	 */
	public static final long HIGH_BASE = 0xD800L;
	public static final long LOW_BASE = 0xDC00L;

	public static final long UTFCODE_HIGH_MASK = 0xFFFF0000L; // utfcode��16λ����
	public static final long UTFCODE_LOW_MASK = 0xFFFFL; // utfcode��16λ����
	
	public static final long UNICODE_HIGH_MASK = 0xFFC00L; // unicode��10λ����
	public static final long UNICODE_LOW_MASK = 0x3FFL; // unicode��10λ����
	
	public long encode(long unicode) {
		if (unicode < 0 || unicode > UNICODE_DWORD_END_POS) {
			return -1;
		}
		
		if (unicode < UNICODE_DWORD_START_POS) {
			return unicode;
		}
		
		unicode -= UNICODE_DWORD_START_POS;
		
		long high = (HIGH_BASE | ((unicode & UNICODE_HIGH_MASK) >>> 10L)) << 16L;
		long low = LOW_BASE | (unicode & UNICODE_LOW_MASK);
		return high | low;
	}

	public long decode(long utfcode) {
		if (utfcode < 0 || utfcode > 0xDBFFDFFFL) {
			return -1;
		}
		
		if (utfcode < UNICODE_DWORD_START_POS) {
			return utfcode;
		}
		
		long high = ((utfcode & UTFCODE_HIGH_MASK) - (HIGH_BASE << 16L)) >>> 6L;
		long low = (utfcode & UTFCODE_LOW_MASK) - 0xDC00L;
		return (high | low) + UNICODE_DWORD_START_POS;
	}

}
