package com.ledo.sdxl.codec;

/**
 * 
 * @author CaiJiahe
 *
 */
public interface Codec {

  /**
   * ��unicode����
   * 
   * @param unicode
   * @return
   */
   public long encode(long unicode);

   /**
    * �ӱ�����unicode
    * 
    * @param utfcode
    * @return
    */
   public long decode(long utfcode);

}
