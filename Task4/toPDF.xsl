<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:output method="xml"
                encoding="UTF-8"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="FilmsSummary"
                                       page-height="29.7cm"
                                       page-width="21cm"
                                       margin-top="2cm"
                                       margin-bottom="5cm"
                                       margin-left="2.5cm"
                                       margin-right="2.5cm">
                    <fo:region-body margin-top="2cm"/>
                    <fo:region-before extent="3cm" />
                    <fo:region-after extent="1.5cm" />
                    <fo:region-start extent="0.5cm" />
                    <fo:region-end extent="0.5cm" />
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="FilmsSummary">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center" font-family="verdana" font-size="25px">
                        <fo:block font-weight="bold">
                            <xsl:text>Podsumowanie filmów</xsl:text>
                        </fo:block>
                        <fo:block text-align="center" font-family="monospace" font-size="8px">
                            <xsl:text>Data wygenerowania</xsl:text>
                            <xsl:text>&#032;</xsl:text>
                            <xsl:value-of select="//Generated"/>
                        </fo:block>
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-family="monospace" margin-top="4cm" font-size="8px">
                        <xsl:text>( Strona </xsl:text>
                        <fo:page-number />
                        <xsl:text>)</xsl:text>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/>
                </fo:flow>

            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="Author">
        <fo:block font-size="10px" text-align="left" font-family="verdana" margin="10">
            <xsl:text>Autor:</xsl:text>
            <fo:block margin-left="2cm">
                <xsl:value-of select="./Name"/>
                <xsl:text>&#032;</xsl:text>
                <xsl:value-of select="./EMail"/>
            </fo:block>
        </fo:block>
    </xsl:template>

    <xsl:template match="Categories">
        <fo:block font-size="12px" text-align="left" font-family="verdana">
            <fo:block font-size="14px" text-align="center" font-weight="bold" margin-bottom="10px">
                <xsl:text>Kategorie</xsl:text>
            </fo:block>
            <fo:block font-size="14px" text-align="center" font-weight="italic" margin-bottom="8px">
                <xsl:text>Ilość kategorii: </xsl:text>
                <xsl:value-of select="TotalCategories"/>
            </fo:block>

            <fo:table border="solid black" width="100%" font-size="8px">
                <fo:table-header>
                    <fo:table-row>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Nazwa</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Ilość filmów</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-header>

                <fo:table-body>
                    <xsl:for-each select="Category">
                        <xsl:sort select="Name"/>
                        <fo:table-row>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./Name" />
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./Count" />
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="Actors">
        <fo:block font-size="12px" text-align="left" font-family="verdana">
            <fo:block font-size="14px" text-align="center" font-weight="bold" margin-bottom="10px">
                <xsl:text>Aktorzy</xsl:text>
            </fo:block>

            <fo:table border="solid black" width="100%" font-size="8px">
                <fo:table-header>
                    <fo:table-row>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Imię</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Ilość filmów</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-header>

                <fo:table-body>
                    <xsl:for-each select="Actor">
                        <xsl:sort select="Name"/>
                        <fo:table-row>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./Name" />
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./Roles" />
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="Films">
        <fo:block font-size="12px" text-align="left" font-family="verdana">
            <fo:block font-size="14px" text-align="center" font-weight="bold" margin-bottom="10px">
                <xsl:text>Filmy</xsl:text>
            </fo:block>
            <fo:block font-size="14px" text-align="center" font-weight="italic" margin-bottom="8px">
                <xsl:text>Ilość filmów: </xsl:text>
                <xsl:value-of select="TotalFilms"/>
            </fo:block>

            <fo:table border="solid black" width="100%" font-size="8px">
                <fo:table-header>
                    <fo:table-row>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Nazwa</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black">
                            <fo:block font-weight="bold" text-align="center">
                                <xsl:text>Średnia ocena</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-header>

                <fo:table-body>
                    <xsl:for-each select="Film">
                        <xsl:sort select="Name"/>
                        <fo:table-row>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./Name" />
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="solid black">
                                <fo:block text-align="center">
                                    <xsl:value-of select="./AvgScore" />
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

</xsl:stylesheet>