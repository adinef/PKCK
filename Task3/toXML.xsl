<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"
                version="1.0"
                encoding="UTF-8"
                media-type="text/xml"
                omit-xml-declaration="no"
                indent="yes"
                xmlns:xalan="http://xml.apache.org/xslt"
                xalan:indent-amount="4"
                />

    <xsl:strip-space elements="*"/>

    <xsl:key name="CategoryKey" match="/FilmDatabase/Categories/Category" use="@catId"/>
    <xsl:key name="ActorKey" match="//Films/Film/Lead" use="@leadId"/>


    <xsl:template match="/">
        <xsl:element name="FilmDatabaseSummary">
            <xsl:call-template name="Author"/>
            <xsl:call-template name="CategoriesSummary"/>
            <xsl:call-template name="ActorsSummary"/>
            <xsl:call-template name="FilmsShort"/>
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
        <Categories>
            <TotalCategories>
                <xsl:value-of select="count(/FilmDatabase/Categories/Category)"/>
            </TotalCategories>
            <xsl:for-each select="/FilmDatabase/Categories/Category">
                <Category>
                    <Name>
                        <xsl:value-of select="text()"/>
                    </Name>
                    <Count>
                        <xsl:value-of select="count(//Films/Film/Categories/Category[@catRefId=current()/@catId])"/>
                    </Count>
                </Category>
            </xsl:for-each>
        </Categories>
    </xsl:template>

    <xsl:template match="//Films/Film/Lead" name="ActorsSummary">
        <Actors>
            <xsl:for-each select="//Films/Film/Lead">
                <xsl:if test="current()/@leadId">
                    <Actor>
                        <Name>
                            <xsl:value-of select="current()/Name"/>
                        </Name>
                        <LastName>
                            <xsl:value-of select="current()/LastName"/>
                        </LastName>
                        <Roles>
                            <xsl:value-of select="count(//Films/Film/Lead[@leadId=current()/@leadId]) +
                                              count(//Films/Film/Lead[@leadRefId=current()/@leadId])"/>
                        </Roles>
                    </Actor>
                </xsl:if>
            </xsl:for-each>
        </Actors>
    </xsl:template>

    <xsl:template match="//Films/Film" name="FilmsShort">
        <Films>
            <TotalFilms>
                <xsl:value-of select="count(//Films/Film)"/>
            </TotalFilms>
            <xsl:for-each select="//Films/Film">
                <Film>
                    <Name>
                        <xsl:value-of select="current()/Name"/>
                    </Name>
                    <AvgScore>
                        <xsl:value-of select="current()/AvgScore"/>
                    </AvgScore>
                </Film>
            </xsl:for-each>
        </Films>
    </xsl:template>

</xsl:stylesheet>
