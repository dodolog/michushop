<?xml version="1.0"?>
<!-- Edited by Michal Olejniczak -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

<xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

<xsl:template match="basket">
  <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
        	
        	<fo:block font-weight="bold" font-size="14pt" text-align="center">michushop </fo:block>
        	<fo:block font-weight="bold" font-size="11pt" text-align="left">Your Shopping Cart:  </fo:block>
        
        	<xsl:apply-templates select="book" />
        </fo:flow>
       </fo:page-sequence>
    </fo:root>
</xsl:template>

<xsl:template match="book">

		<fo:block font-weight="bold" font-size="11pt" text-align="center">
			<xsl:value-of select="title" />
		</fo:block>		
		<fo:block font-weight="italic" font-size="10pt" >
			<xsl:value-of select="description" />
		</fo:block>
		<fo:block font-weight="bold" font-size="10pt">
			<xsl:value-of select="price" /> zl
		</fo:block>
	
</xsl:template>

</xsl:stylesheet> 