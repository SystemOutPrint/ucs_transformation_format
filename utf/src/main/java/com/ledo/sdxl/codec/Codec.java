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
   public long encode(long unicode);

   /**
    * 从编码解成unicode
    * 
    * @param utfcode
    * @return
    */
   public long decode(long utfcode);

}
