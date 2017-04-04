package com.ledo.sdxl.encoder;

import org.junit.Test;

import junit.framework.Assert;

public class TestUTF16 {

	Codec codec = new UTF16();
	
	@Test
	public void test2BytesEncode() {
		Assert.assertEquals(0x0, codec.encode(0x0));
		Assert.assertEquals(0xFFFF, codec.encode(0xFFFF));
	}
	
	@Test
	public void test4BytesEncode() {
		Assert.assertEquals(0xD800DC00L, codec.encode(0x10000));
		Assert.assertEquals(0xFF, codec.encode(0xFF));
	}
	
}
