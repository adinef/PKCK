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
                <xsl:call-template name="Content"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="Styles">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              xmlns="http://www.w3.org/1999/xhtml"/>
    </xsl:template>

    <xsl:template name="Content">
        <div xmlns="http://www.w3.org/1999/xhtml" class="container">
            <xsl:call-template name="Author"/>
            <xsl:call-template name="Films"/>
            <xsl:call-template name="Categories"/>
            <xsl:call-template name="Actors"/>
        </div>
    </xsl:template>

    <xsl:template match="//Author" name="Author">
        <div xmlns="http://www.w3.org/1999/xhtml" class="card mb-3">
            <div class="card-body">
                <h4 class="card-title">Autor</h4>
                <hr/>
                <div class="card-text">
                    <div>
                        <xsl:value-of select="//Author/Name"/>
                    </div>
                    <div>
                        <xsl:value-of select="//Author/EMail"/>
                    </div>
                    <div class="small">
                        <span class="mr-1">Wygenerowano</span>
                        <span><xsl:value-of select="//Generated"/> </span>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="//Categories" name="Categories">
        <div xmlns="http://www.w3.org/1999/xhtml" class="card mb-3">
            <div class="card-body">
                <h4 class="card-title">Podsumowanie kategorii</h4>
                <hr/>
                <h5>Łacznie kategorii:</h5>
                <div>
                    <xsl:value-of select="//Categories/TotalCategories"/>
                </div>
                <hr/>
                <h5>Kategorie (alfabetycznie)</h5>
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Kategoria</th>
                            <th scope="col">Ilość filmów</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="//Categories/Category">
                            <xsl:sort select="Name"/>
                            <tr>
                                <td>
                                    <span>
                                        <xsl:value-of select="Name"/>
                                    </span>
                                </td>
                                <td>
                                    <h3>
                                        <span class="badge badge-primary badge-pill">
                                            <xsl:value-of select="Count"/>
                                        </span>
                                    </h3>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="//Actors" name="Actors">
        <div xmlns="http://www.w3.org/1999/xhtml" class="card mb-3">
            <div class="card-body">
                <h4 class="card-title">Podsumowanie aktorów</h4>
                <hr/>
                <h5>Aktorzy (zgodnie z ilością zagranych filmów)</h5>
                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">Imię i nazwisko</th>
                            <th scope="col">Ilość filmów</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="//Actors/Actor">
                            <xsl:sort select="Roles" order="descending"/>
                            <tr>
                                <td>
                                    <span>
                                        <xsl:value-of select="Name"/>
                                    </span>
                                    <span>
                                        <xsl:value-of select="LastName"/>
                                    </span>
                                </td>
                                <td>
                                    <h3>
                                        <span class="badge badge-primary badge-pill">
                                            <xsl:value-of select="Roles"/>
                                        </span>
                                    </h3>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="//Films" name="Films">
        <div xmlns="http://www.w3.org/1999/xhtml" class="card mb-3">
            <div class="card-body">
                <h4 class="card-title">Filmy</h4>
                <hr/>
                <h5>Wszystkich filmów:</h5>
                <div>
                    <h1>
                        <span class="badge badge-secondary">
                            <xsl:value-of select="//Films/TotalFilms"/>
                        </span>
                    </h1>
                </div>
                <hr/>
                <h4>Średnia ocena wszystkich filmów:</h4>
                <div>
                    <h1>
                        <span class="badge badge-secondary">
                            <xsl:value-of select="substring(string(avg(//Films/Film/AvgScore)), 0, 5)"/>
                        </span>
                    </h1>
                </div>
                <hr/>
                <h5>Lista filmów</h5>
                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">Tytuł</th>
                            <th scope="col">Średni wynik</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="//Films/Film">
                            <xsl:sort select="Roles" order="descending"/>
                            <tr>
                                <td>
                                    <span>
                                        <xsl:value-of select="Name"/>
                                    </span>
                                </td>
                                <td>
                                    <h2>
                                        <xsl:choose>
                                            <xsl:when test="AvgScore &gt; 7.99">
                                                <span class="badge badge-info">
                                                    <xsl:value-of select="AvgScore"/>
                                                </span>
                                            </xsl:when>
                                            <xsl:when test="AvgScore &gt; 5.99">
                                                <span class="badge badge-success">
                                                    <xsl:value-of select="AvgScore"/>
                                                </span>
                                            </xsl:when>
                                            <xsl:when test="AvgScore &gt; 3.99">
                                                <span class="badge badge-warning">
                                                    <xsl:value-of select="AvgScore"/>
                                                </span>
                                            </xsl:when>
                                            <xsl:when test="AvgScore &gt; 0">
                                                <span class="badge badge-danger">
                                                    <xsl:value-of select="AvgScore"/>
                                                </span>
                                            </xsl:when>
                                        </xsl:choose>
                                    </h2>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
