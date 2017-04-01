# UTF

全称UCS Transformation format

## 0x01 UCS
全称Universal Multiple-Octet Coded Character Set，是字符的一个编码方案，分为UCS-2、UCS-4。<br/>
__UCS-2：__总计有2^16=65536个码位。
__UCS-4：__总计有2^31=2147483648个码位，根据最高位为0的最高字节分成2^7=128个group。每个group再根据次高字节分为256个plane。每个plane根据第3个字节分为256行 (rows)，每行包含256个cells。
__BMP：__UCS-4的group0、plane0的码位被称为BMP，等同于UCS-2。

## 0x02 UTF-8
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

## 0x03 UTF-16
Unicode有16的辅助平面，和一个BMP。<br/>
BMP可以使用一个字节来表示。而辅助平面的unicode要使用两个字节来表示。<br/><br/>
具体算法：<br/>
	n = unicode - 0x10000(0x10000 <= unicode <= 0x10FFFF)<br/>
所以0x0000 <= n <= 0xFFFFF<br/>
n的高10位加上D800就是第一个字节，n的低10位加上DC00就是第二个字节。<br/>
所以UTF-16表示的范围是16*2^16个字符。<br/>