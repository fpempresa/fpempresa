<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformar-municipios.xsl
    Created on : December 15, 2016, 6:05 PM
    Author     : ruben
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <head>
            <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"></link>
        </head>
        <div class="row">
            <xsl:for-each select="resultset/row">
                <div class="col-xs-3">
                    <div class="col-xs-4">
                        <strong><xsl:value-of select="field[@name='idMunicipio']"></xsl:value-of></strong>
                    </div>
                    <div class="col-xs-8">
                        <xsl:value-of select="field[@name='descripcion']"></xsl:value-of>
                    </div>
                </div>
            </xsl:for-each>
        </div>
    </xsl:template>

</xsl:stylesheet>
