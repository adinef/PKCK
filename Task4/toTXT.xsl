<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text"
                encoding="UTF-8"
                indent="yes"
    />

    <xsl:strip-space elements="*"/>

    <xsl:template match="/FilmDatabaseSummary">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Author" name="Authors">
        <xsl:call-template name="columnHeader">
            <xsl:with-param name="content" select="'Autor'"/>
        </xsl:call-template>
        <xsl:value-of select="Name"/>
        <xsl:text>, </xsl:text>
        <xsl:value-of select="EMail"/>
        <xsl:text>&#xa;</xsl:text>
    </xsl:template>

    <xsl:template match="Generated" name="Generated">
        <xsl:call-template name="columnHeader">
            <xsl:with-param name="content" select="'Wygenerowano'"/>
        </xsl:call-template>
        <xsl:value-of select="."/>
        <xsl:text>&#xa;</xsl:text>
    </xsl:template>


    <xsl:template match="Categories" name="Categories">
        <xsl:call-template name="printLine">
            <xsl:with-param name="content" select='_'/>
        </xsl:call-template>
        <xsl:text>&#xa;</xsl:text>
        <xsl:call-template name="columnHeader">
            <xsl:with-param name="content" select="'Kategorie: '" />
        </xsl:call-template>
        <xsl:value-of select="TotalCategories"/>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Category">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="15"/>
                <xsl:with-param name="content" select="./Name"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Category">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="15"/>
                <xsl:with-param name="content" select="./Count"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
    </xsl:template>


    <xsl:template match="Actors" name="Actors">
        <xsl:call-template name="printLine">
            <xsl:with-param name="content" select='_'/>
        </xsl:call-template>
        <xsl:text>&#xa;</xsl:text>
        <xsl:call-template name="columnHeader">
            <xsl:with-param name="content" select="'Aktorzy: '" />
        </xsl:call-template>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Actor">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="20"/>
                <xsl:with-param name="content" select="concat(./Name, ' ', ./LastName)"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Actor">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="20"/>
                <xsl:with-param name="content" select="./Roles"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
    </xsl:template>


    <xsl:template match="Films" name="Films">
        <xsl:call-template name="printLine">
            <xsl:with-param name="content" select='_'/>
        </xsl:call-template>
        <xsl:text>&#xa;</xsl:text>
        <xsl:call-template name="columnHeader">
            <xsl:with-param name="content" select="'Aktorzy: '" />
        </xsl:call-template>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Film">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="20"/>
                <xsl:with-param name="content" select="./Name"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="Film">
            <xsl:call-template name="spaceBefore">
                <xsl:with-param name="length" select="20"/>
                <xsl:with-param name="content" select="./AvgScore"/>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
    </xsl:template>

    <xsl:template name="addSpace">
        <xsl:param name="content"/>
        <xsl:param name="length"/>
        <xsl:choose>
            <xsl:when test="string-length($content) &lt; $length">
                <xsl:text>&#xa;</xsl:text>
                <xsl:call-template name="addSpace">
                    <xsl:with-param name="content" select="concat($content, ' ')"/>
                    <xsl:with-param name="length" select="$length"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>&#xa;</xsl:text>
                <xsl:value-of select="substring($content, 1, $length)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="spaceBefore">
        <xsl:param name="content"/>
        <xsl:param name="length"/>
        <xsl:choose>
            <xsl:when test="string-length($content) &lt; $length">
                <xsl:call-template name="spaceBefore">
                    <xsl:with-param name="content" select="concat(' ',$content)"/>
                    <xsl:with-param name="length" select="$length"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="substring($content, 1, $length)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="printLine">
        <xsl:param name="content"/>
        <xsl:choose>
            <xsl:when test="string-length($content) &lt; 75">
                <xsl:call-template name="printLine">
                    <xsl:with-param name="content" select="concat($content, '-')"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="substring($content, 1, 75)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="columnHeader">
        <xsl:param name="content"/>
        <xsl:text>| </xsl:text>
        <xsl:value-of select="$content"/>
        <xsl:text> | </xsl:text>
    </xsl:template>


</xsl:stylesheet>
