# UTF

全称UCS Transformation format

## 0x01 UCS
全称Universal Multiple-Octet Coded Character Set，是字符的一个编码方案，分为UCS-2、UCS-4。<br/>
__UCS-2__：总计有2^16=65536个码位。<br/>
__UCS-4__：总计有2^31=2147483648个码位，根据最高位为0的最高字节分成2^7=128个group。每个group再根据次高字节分为256个plane。每个plane根据第3个字节分为256行 (rows)，每行包含256个cells。<br/>
__BMP__：UCS-4的group0、plane0的码位被称为BMP，等同于UCS-2。<br/><br/>
　　因为码位的长度越来越长，所以简单的单字节、双字节编码无法容纳所有的码位，对码位的编码需要尽可能的保证容纳更多的字符，而且要尽可能的节省空间。<br/>
　　根据编码长度可以划分成单字节编码、双字节编码、四字节编码，编码单元的长度越长，容纳的字符越多，但是浪费的空间也越大，常用字符并不多，如果采用短字节编码，会导致非常用字符无法显示，造成乱码。如果采用长字节编码，尽管能表示很多字符，但是造成的空间浪费是很大的。</br>
　　所以变长编码应运而生，对于常用字符采用短字符编码，对于非常用字符采用变长编码，既不会浪费空间，又可以容纳更多的字符。

## 0x02 UTF-16
　　随着码位的越来越长，字符数早已超过65536个了，所以现在的通常使用的都是UCS4编码。目前UCS4只编码了17个平面，共0x10FFFF个字符。对于0x0000到0xFFFF之间的编码，还是采用双字节编码，并且码位和二进制位一一对应。对于超过0xFFFF的码位，我们采用四字节编码。<br/>
　　Unicode有16个辅助平面，和一个BMP，总计17个平面，因为每个平面会占用2^16个字符，所以17个平面可以表示0x000000到0x10FFFF这么多字符，其中为了实现UTF16编码，Unicode保留了0XD800到0XDFFF这段码位，没有分给其他字符。<br/>
　　在UTF16编码中，0x0000到0xFFFF使用双字节来表示。辅助平面的Unicode则需要使用四个字节。<br/><br/>
__具体算法__：<br/>

　　unicode = 辅助平面的unicode代码点<br/>
　　n = unicode - 0x10000(0x10000 <= unicode <= 0x10FFFF)<br/>
	
所以得出0x0000 <= n <= 0xFFFFF<br/>
D800+n的高10位构成第一个字节，DC00+n的低10位构成第二个字节。<br/>
所以UTF-16表示的码位范围是0~17*2^16个字符。<br/>

__例子__：<br/>

　　unicode = 0xABCDE<br/>
　　n = 0xABCDE - 0x10000<br/>
　　n = 0x9BCDE<br/>
　　n = 1001 1011 1100 1101 1110(二进制)<br/>
　　0xD800 = 1101 1000 0000 0000 <br/>
　　n的高十位 + 0xD800 = 1101 1010 0110 1111<br/>
　　n的低十位 + 0xDC00 = 1101 1100 1101 1110<br/>
　　最后的结果 = 1101 1010 0110 1111 1101 1100 1101 1110 = DA 6F DC DE <br/>

因为UTF-16的编码单元是双字节，所以在传输的过程中字节序的问题很关键。<br/>
__大端__：高字节存储在高位（0XABCD => AB CD），又称网络字节序。<br/>
__小端__：低字节存储在高位（0xABCD => CD AB）。<br/>

## 0x03 UTF-8
　　UTF-8的编码单元是单字节的，所以不存在字节序的问题，UTF-8的编码长度存储在起始字节，如果起始字节以0开始代表是单字节的，否则起始字节从高位开始有N个连续的1就代表后面该字符编码占用N个字节（2 =< N <= 6）。UTF-8就像一列火车，第一个字节是车头，后面每个字节是车厢，其中承载的货物是UCS编码。UTF-8规定承载的UCS编码以大端表示，也就是说第一个字节中的x是UCS编码的高位，后面字节中的x是UCS编码的低位。<br/><br/>
举个例子，unicode为0xAEAD的字符，转成UTF-8编码的过程如下：<br/>

　　先判断0xAEAD在0x0080 0000 07FF到0000 0800-0000 FFFF之间。<br/>
　　然后计算0xAEAD的二进制值 b = 1010 1110 1010 1101<br/>
　　然后根据1110xxxx 10xxxxxx 10xxxxxx从低位向高位填充b<br/>
　　得到最终结果 11101010 10111010 10101101<br/>

<div>
	<table border="0">
	  <tr>
	    <th>范围</th>
	    <th>编码方式</th>
	  </tr>
	  <tr>
	    <td>0000 0000-0000 007F</td>
	    <td>0xxxxxxx</td>
	  </tr>
	  <tr>
	    <td>0000 0080-0000 07FF</td>
	    <td>110xxxxx 10xxxxxx</td>
	  </tr>
	  <tr>
	    <td>0000 0800-0000 FFFF</td>
	    <td>1110xxxx 10xxxxxx 10xxxxxx</td>
	  </tr>
	  <tr>
	    <td>0001 0000-0010 FFFF</td>
	    <td>11110xxx 10xxxxxx 10xxxxxx 10xxxxxx</td>
	  </tr>
	  <tr>
	    <td>0002 0000-03FF FFFF</td>
	    <td>111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx</td>
	  </tr>
	  <tr>
	    <td>0400 0000-7FFF FFFF</td>
	    <td>1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx</td>
	  </tr>
	</table>
</div>

