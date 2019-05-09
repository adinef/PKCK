<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"
                doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
                indent="yes"
                xmlns:xalan="http://xml.apache.org/xslt"
                xalan:indent-amount="4"
    />

    <xsl:strip-space elements="*"/>

    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <title>Podsumowanie</title>
                <xsl:call-template name="Styles"/>
            </head>
            <body>
                <p>Hey!</p>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="Styles">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              xmlns="http://www.w3.org/1999/xhtml"/>
    </xsl:template>

</xsl:stylesheet>
