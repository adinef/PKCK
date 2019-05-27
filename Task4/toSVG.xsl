<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:svg="http://www.w3.org/2000/svg"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink">

    <xsl:output method="xml"
                media-type="image/svg"
                encoding="utf-8"
                indent="yes"
                doctype-public="-//W3C//DTD SVG 1.1//EN"
                doctype-system="http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"/>

    <xsl:template match="/">

        <svg:svg width="1280" height="768" font-family="Verdana">
            <svg:desc>Biblioteka filmów, dane</svg:desc>
            <svg:title>Raport, biblioteka filmów</svg:title>

            <svg:defs>
                <svg:linearGradient id="gradient">
                    <svg:stop offset="0%" style="stop-color: #000000;"/>
                    <svg:stop offset="100%" style="stop-color: #ffebf4;"/>
                </svg:linearGradient>
            </svg:defs>

            <!--<svg:rect x="0" y="0" width="840" height="500" fill="url(#gradient)" stroke-width="8" stroke="#FFDC23"/>-->

            <svg:text x="430" y="55" font-size="28" fill="white" stroke="black" stroke-width="1" font-weight="bold"
                      text-anchor="middle">
                Filmy (raport)
            </svg:text>

            <xsl:call-template name="Buttons"/>
            <xsl:call-template name="Author"/>

            <style>
                g.button:hover {
                fill: #FFFFEE;
                }

                .bar {
                fill: #aaa;
                height: 21px;
                transition: fill .3s ease;
                cursor: pointer;
                font-family: Helvetica, sans-serif;

                text {
                fill: #555;
                }
                }

                .chart:hover,
                .chart:focus {
                .bar {
                fill: #aaa;
                }
                }

                .bar:hover,
                .bar:focus {
                fill: red !important;

                text {
                fill: red;
                }
                }

                figcaption {
                font-weight: bold;
                color: #000;
                margin-bottom: 20px;
                }

                body {
                font-family: 'Open Sans', sans-serif;
                }

                author {
                    background: rgba(0, 0, 0, 0.7);
                }

            </style>

            <script type="text/javascript">
            </script>

            <xsl:apply-templates/>
        </svg:svg>
    </xsl:template>

    <xsl:template match="Author" name="Author">
        <svg:g id="autor_dane">
            <svg:text x="420" y="420" font-size="16" fill="black" text-anchor="middle" class="author">
                <xsl:value-of select="concat(./Name, ', ', ./EMail)"/>
            </svg:text>
        </svg:g>
    </xsl:template>

    <xsl:template match="Categories">
        <svg:g id="rect">
            <svg:text font-size="16" fill="white" font-weight="bold" text-anchor="middle" x="420" y="110" >
                Kategorie z kardynalnością
            </svg:text>
            <xsl:for-each select="Category">
                <xsl:element name="svg:g">
                    <xsl:attribute name="class">
                        <xsl:value-of select="'bar'"/>
                    </xsl:attribute>
                    <xsl:element name="svg:rect">
                        <xsl:attribute name="width">
                            <xsl:value-of select="number(./Count) * 10"/>
                        </xsl:attribute>
                        <xsl:attribute name="height">
                            <xsl:value-of select="19"/>
                        </xsl:attribute>
                        <xsl:attribute name="y">
                            <xsl:value-of select="position() * 25  +110"/>
                        </xsl:attribute>
                        <xsl:attribute name="x">
                            <xsl:value-of select="320"/>
                        </xsl:attribute>
                    </xsl:element>
                    <xsl:element name="svg:text">
                        <xsl:attribute name="x">
                            <xsl:value-of select="number(./Count) * 10 + 5 + 320 + number(./Count)"/>
                        </xsl:attribute>
                        <xsl:attribute name="dy">
                            <xsl:value-of select="'.35em'"/>
                        </xsl:attribute>
                        <xsl:attribute name="y">
                            <xsl:value-of select="position() * 25 + 10 + 110"/>
                        </xsl:attribute>
                            <xsl:value-of select="concat(./Count, ' - ', ./Name)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </svg:g>
    </xsl:template>

    <xsl:template name="Buttons">
        <a xlink:href="biblioteka_output.xhtml" target="_blank">
            <svg:g class="button" cursor="pointer">
                <svg:rect x="15" y="15" width="100" height="60" fill="#C4C4C4" stroke="black"/>
                <svg:text x="38" y="40" fill="white" font-size="16">Raport</svg:text>
                <svg:text x="38" y="60" fill="white" font-size="16">XHTML</svg:text>
            </svg:g>
        </a>

        <a xlink:href="biblioteka_output.pdf" target="_blank">
            <svg:g class="button" cursor="pointer">
                <svg:rect x="15" y="90" width="100" height="60" fill="#C4C4C4" stroke="black"/>
                <svg:text x="38" y="115" fill="white" font-size="16">Raport</svg:text>
                <svg:text x="38" y="135" fill="white" font-size="16">PDF</svg:text>
            </svg:g>
        </a>

        <a xlink:href="biblioteka_output.xml" target="_blank">
            <svg:g class="button" cursor="pointer">
                <svg:rect x="15" y="240" width="100" height="60" fill="#C4C4C4" stroke="black"/>
                <svg:text x="38" y="265" fill="white" font-size="16">Raport</svg:text>
                <svg:text x="38" y="285" fill="white" font-size="16">XML</svg:text>
            </svg:g>
        </a>

        <a xlink:href="biblioteka_output.txt" target="_blank">
            <svg:g class="button" cursor="pointer">
                <svg:rect x="15" y="315" width="100" height="60" fill="#C4C4C4" stroke="black"/>
                <svg:text x="38" y="335" fill="white" font-size="16">Raport</svg:text>
                <svg:text x="38" y="355" fill="white" font-size="16">TXT</svg:text>
            </svg:g>
        </a>
    </xsl:template>

    <!--<xsl:template match="ZrealizowaneUsługi"/>-->
    <!--<xsl:template match="IlośćCzytelników"/>-->
    <!--<xsl:template match="Obrót"/>-->
    <!--<xsl:template match="DataWygenerowania"/>-->

</xsl:stylesheet>
