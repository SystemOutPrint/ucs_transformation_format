# UTF

ȫ��UCS Transformation format

## 0x01 UCS
ȫ��Universal Multiple-Octet Coded Character Set�����ַ���һ�����뷽������ΪUCS-2��UCS-4��<br/>
__UCS-2__���ܼ���2^16=65536����λ��<br/>
__UCS-4__���ܼ���2^31=2147483648����λ���������λΪ0������ֽڷֳ�2^7=128��group��ÿ��group�ٸ��ݴθ��ֽڷ�Ϊ256��plane��ÿ��plane���ݵ�3���ֽڷ�Ϊ256�� (rows)��ÿ�а���256��cells��<br/>
__BMP__��UCS-4��group0��plane0����λ����ΪBMP����ͬ��UCS-2��<br/>

## 0x02 UTF-8
<div>
	<table border="0">
	  <tr>
	    <th>��Χ</th>
	    <th>���뷽ʽ</th>
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
Unicode��15������ƽ�棬��һ��BMP��BMP�е�0XD800��0XDFFF�Ǳ����ģ�û�зָ��κε��ַ���<br/>
��UTF16�����У�BMP����ʹ�������ֽ�����ʾ��������ƽ���unicode����Ҫʹ���ĸ��ֽ�����ʾ����Ϊ�����Ĵ�С����2^20��<br/><br/>
__�����㷨__��<br/>
	unicode = ����ƽ���unicode�����
	n = unicode - 0x10000(0x10000 <= unicode <= 0x10FFFF)<br/>
���Եó�0x0000 <= n <= 0xFFFFF<br/>
n�ĸ�10λ����D800���ǵ�һ���ֽڣ�n�ĵ�10λ����DC00���ǵڶ����ֽڡ�<br/>
����UTF-16��ʾ�ķ�Χ��16*2^16���ַ���<br/>