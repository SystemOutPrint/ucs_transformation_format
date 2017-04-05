package com.ledo.sdxl.codec;

/**
 * 
 * @author CaiJiahe
 *
 */
public interface Codec {

  /**
   * 将unicode编码
   * 
   * @param unicode
   * @return
   */
   long encode(long unicode);

   /**
    * 从编码解成unicode
    * 
    * @param utfcode
    * @return
    */
   long decode(long utfcode);

}
