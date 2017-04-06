package com.ledo.sdxl.codec;

/**
 * 
 * @author CaiJiahe
 *
 */
public interface Codec {

  /**
   * unicode的utf编码
   * 
   * @param unicode
   * @return -1表示溢出
   */
   long encode(long unicode);

   /**
    * 将utf编码解码成unicode
    * 
    * @param utfcode
    * @return -1表示溢出
    */
   long decode(long utfcode);

}
