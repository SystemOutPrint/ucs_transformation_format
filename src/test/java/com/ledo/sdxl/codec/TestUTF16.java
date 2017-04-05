package com.ledo.sdxl.codec;

import org.junit.Assert;
import org.junit.Test;

public class TestUTF16 {

	private Codec codec = new UTF16();
	
	@Test
	public void test2BytesEncode() {
		Assert.assertEquals(0x0, codec.encode(0x0));
		Assert.assertEquals(0xFF, codec.encode(0xFF));
		Assert.assertEquals(0xFFFF, codec.encode(0xFFFF));
	}
	
	@Test
	public void test4BytesEncode() {
		Assert.assertEquals(0xD800DC00L, codec.encode(0x10000));
		Assert.assertEquals(0xDBFFDFFFL, codec.encode(0x10FFFFL));
	}
	
	@Test
	public void test2BytesDecode() {
		Assert.assertEquals(0x0, codec.decode(0x0));
		Assert.assertEquals(0xFF, codec.decode(0xFF));
		Assert.assertEquals(0xFFFF, codec.decode(0xFFFF));
	}
	
	@Test
	public void test4BytesDecode() {
		Assert.assertEquals(0x10000, codec.decode(0xD800DC00L));
		Assert.assertEquals(0x10FFFFL, codec.decode(0xDBFFDFFFL));
	}
	
}
