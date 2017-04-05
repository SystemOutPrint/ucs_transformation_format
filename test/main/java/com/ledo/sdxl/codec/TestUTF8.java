package com.ledo.sdxl.encoder;

import org.junit.Test;


import junit.framework.Assert;

public class TestUTF8 {
	
	Codec codec = new UTF8();
	
	@Test
	public void test1ByteEncode() {
		Assert.assertEquals(0L, codec.encode(0x0));
		Assert.assertEquals(77L, codec.encode(0x4D));
	}
	
	@Test
	public void test2ByteEncode() {
		Assert.assertEquals(49792L, codec.encode(0x80));
		Assert.assertEquals(57279L, codec.encode(0x7FF));
	}

	@Test
	public void test3ByteEncode() {
		Assert.assertEquals(14721152L, codec.encode(0x800));
		Assert.assertEquals(15380365L, codec.encode(0xABCD));
	}
	
	@Test
	public void test4ByteEncode() {
		Assert.assertEquals(4071338910L, codec.encode(0xABCDE));
	}
	
	@Test
	public void test5ByteEncode() {
		Assert.assertEquals(1068016383919L, codec.encode(0xABCDEF));
	}
	
	@Test
	public void test6ByteEncode() {
		Assert.assertEquals(277672580987839L, codec.encode(0XABCDEFF));
	}
	
	@Test
	public void testOverflowEncode() {
		Assert.assertEquals(-1L, codec.encode(0xABCDEFFF));
		Assert.assertEquals(-1L, codec.encode(-1));
	}
	
	@Test
	public void test1ByteDecode() {
		Assert.assertEquals(0L, codec.decode(0x0));
		Assert.assertEquals(77L, codec.decode(0x4D));
	}
	
	@Test
	public void test2ByteDecode() {
		Assert.assertEquals(0x400, codec.decode(0xD080));
		Assert.assertEquals(0x7FF, codec.decode(0xDFBF));
	}

	@Test
	public void test3ByteDecode() {
		Assert.assertEquals(0x8000L, codec.decode(0xE88080L));
		Assert.assertEquals(0xFFFFL, codec.decode(0xEFFFFFL));
	}
	
	@Test
	public void test4ByteDecode() {
		Assert.assertEquals(0x100000, codec.decode(0xF4808080L));
	}
	
	@Test
	public void test5ByteDecode() {
		Assert.assertEquals(0x02000000L, codec.decode(0xFA80808080L));
	}
	
	@Test
	public void test6ByteDecode() {
		Assert.assertEquals(0x40000000L, codec.decode(0xFD8080808080L));
		Assert.assertEquals(0x7FFFFFFFL, codec.decode(0xFDBFBFBFBFBFL));
	}
	
	@Test
	public void testOverflowDecode() {
		Assert.assertEquals(-1L, codec.decode(0xFFFFFFFFFFFFL));
		Assert.assertEquals(-1L, codec.decode(-1));
	}
	
	
}
