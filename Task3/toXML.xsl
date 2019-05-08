<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"
                version="1.0"
                encoding="UTF-8"
                media-type="text/xml"
                omit-xml-declaration="no"
                indent="yes"/>

    <xsl:strip-space elements="*"/>


    <xsl:key name="CategoryKey" match="/FilmDatabase/Categories/Category" use="@catId"/>
    <xsl:key name="ActorKey" match="//Films/Film/Lead" use="@leadId"/>



    <xsl:template match="/">
        <xsl:element name="FilmDatabaseSummary">
            <xsl:call-template name="Author"/>
            <xsl:call-template name="CategoriesSummary"/>
            <xsl:call-template name="ActorsSummary"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="//Head" name="Author">
        <Author>
            <Name>
                <xsl:value-of select="//Head/Author"/>
            </Name>
            <EMail>
                <xsl:value-of select="//Head/EMail"/>
            </EMail>
        </Author>
    </xsl:template>

    <xsl:template match="//Categories" name="CategoriesSummary">
        <CatCount>
            <xsl:value-of select="count(/FilmDatabase/Categories/Category)"/>
        </CatCount>
        <xsl:for-each select="/FilmDatabase/Categories/Category">
            <Cat>
                <Name>
                    <xsl:value-of select="text()"/>
                </Name>
                <MoviesWithCategory>
                    <xsl:value-of select="count(//Films/Film/Categories/Category[@catRefId=current()/@catId])"/>
                </MoviesWithCategory>
            </Cat>
        </xsl:for-each>
    </xsl:template>


    <xsl:template match="//Films/Film/Lead" name="ActorsSummary">
        <ActorsCount>

        </ActorsCount>
    </xsl:template>

</xsl:stylesheet>
