<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="5" indent="yes"/>
		<xsl:template match="/">
			<html>
				 <head>
				   <title><xsl:value-of select="BMECAT/HEADER/CATALOG/CATALOG_NAME"/></title>
				 </head>
				 <body>
					 <xsl:apply-templates select="BMECAT"/>
				 </body>
			 </html>
	</xsl:template>
		
	<xsl:template match="HEADER">
		<table>
		<xsl:for-each select="CATALOG/*">
				<tr>
					<td><xsl:value-of select ="local-name()"/></td>
					<td><xsl:value-of select="node()"/></td>
				</tr>
		</xsl:for-each>
		</table>
		<h1><xsl:value-of select ="local-name(//SUPPLIER_NAME)"/><xsl:text>: </xsl:text><xsl:value-of select="//SUPPLIER_NAME"/></h1>
	</xsl:template>
	

	<xsl:template match="//SUPPLIER_AID">
	<h2>Artikel_ID: <xsl:value-of select ="node()"/></h2>
	</xsl:template>	
	
	<xsl:template match="//ARTICLE_DETAILS">
		<p><h3><xsl:value-of select="local-name()"/><xsl:text>: </xsl:text></h3></p>
		<table>
		<xsl:for-each select="*">
			<tr>
				<td><xsl:value-of select ="local-name()"/></td>
				<td><xsl:value-of select ="node()"/></td>
			</tr>
		</xsl:for-each>
	</table>
	</xsl:template>

	<xsl:template match="//ARTICLE_ORDER_DETAILS">
		<p><h3><xsl:value-of select="local-name()"/><xsl:text>: </xsl:text></h3></p>
		<table>
		<xsl:for-each select="*">
			<tr>
				<td><xsl:value-of select ="local-name()"/></td>
				<td><xsl:value-of select ="node()"/></td>
			</tr>
		</xsl:for-each>
	</table>
	</xsl:template>
	
	<xsl:template match="//ARTICLE_PRICE_DETAILS">
		<p><h3><xsl:value-of select="local-name()"/><xsl:text>: </xsl:text></h3></p>
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="//ARTICLE_PRICE">
		<p><h3><xsl:value-of select="local-name()"/><xsl:text>: </xsl:text></h3></p>
		<table>
		<xsl:for-each select="*">
			<tr>
				<td><xsl:value-of select ="local-name()"/></td>
				<td><xsl:value-of select ="node()"/></td>
			</tr>
		</xsl:for-each>
	</table>
	</xsl:template>	
	
	<xsl:template match="//ARTICLE_REFERENCE/ART_ID_TO">
		<p><h4>ARTIKEL HAT EINE REFERENZ AUF: <xsl:value-of select="node()"/><xsl:text>: </xsl:text></h4></p>
	</xsl:template>	
	
</xsl:stylesheet>