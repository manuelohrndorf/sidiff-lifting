<?xml version="1.0" encoding="UTF-8"?>
<!-- This transformation must be applied to UML.xmi before it can
     be loaded properly, as the original model is not well-formed. -->
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xmi="http://www.omg.org/spec/XMI/20131001">

	<!-- Add xmi:type="uml:PrimitiveType" attribute to type elements -->
	<xsl:template match="type[@href and not(@xmi:type)]">
		<xsl:attribute name="xmi:type">uml:PrimitiveType</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:template>

	<!-- Identity transformation -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>