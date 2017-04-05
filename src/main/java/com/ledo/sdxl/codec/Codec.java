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
   long encode(long unicode);

   /**
    * �ӱ�����unicode
    * 
    * @param utfcode
    * @return
    */
   long decode(long utfcode);

}
