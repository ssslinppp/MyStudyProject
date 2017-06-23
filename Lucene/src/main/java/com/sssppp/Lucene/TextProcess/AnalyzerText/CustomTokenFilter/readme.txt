具体步骤：
1. 继承TokenFilter类；
2. 构造函数的输入参数为：TokenStream input
         在构造函数中添加各种属性，如：
   charTermAtt = addAttribute(CharTermAttribute.class);
   posIncrAtt = addAttribute(PositionIncrementAttribute.class);
3. 重新incrementToken()，在该方法中改变token的各种属性，
          如：CharTermAttribute、PositionIncrementAttribute;
   