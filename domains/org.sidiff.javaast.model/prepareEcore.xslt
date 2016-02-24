<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" >



<!-- Author: Timo Kehrer -->


<!-- ID-Attribut von JIdentifiableElement als solches markieren -->
<xsl:template match="eStructuralFeatures[@name='id' and ../@name='JIdentifiableElement']">
	<xsl:copy>
		<xsl:attribute name="iD">true</xsl:attribute>
		<xsl:attribute name="defaultValueLiteral"></xsl:attribute>
		
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>
</xsl:template>

<!-- name und nsUri anpassen -->
<xsl:template match="ecore:EPackage[@name='JavaModel']">
		<xsl:copy>			
			<xsl:attribute name="name">model</xsl:attribute>
			<xsl:attribute name="nsURI">http://www.sidiff.org/org.sidiff.javaast.model</xsl:attribute> 
			<xsl:apply-templates select="attribute::*[not((name()='name') or (name()='nsURI'))] | node()"/>
		</xsl:copy>
</xsl:template>

<xsl:template match="@* | node()">
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>
</xsl:template>

</xsl:stylesheet>