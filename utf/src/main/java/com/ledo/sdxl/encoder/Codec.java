package com.ledo.sdxl.encoder;

public interface Codec {

	public long encode(long unicode);
	
	public long decode(long code);
	
}
