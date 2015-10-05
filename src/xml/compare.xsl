<?xml version="1.0" encoding="UTF-8"?>
<!-- 
  Copyright (c) 2015 Sacred Scripture Foundation.
  "All scripture is given by inspiration of God, and is profitable for 
  doctrine, for reproof, for correction, for instruction in righteousness:
  That the man of God may be perfect, throughly furnished unto all good 
  works." (2 Tim 3:16-17)
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:nls="http://www.sacredscripturefoundation.org/schema/nls"
  version="1.0">

  <xsl:output method="html" encoding="UTF-8" />
  <xsl:param name="lang1" />
  <xsl:param name="lang2" />

  <xsl:template match="/">
    <html>
      <head>
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
      </head>
      <body>
        <table border="1" bordercolor="black">
          <thead>
            <tr>
              <th>Book (<xsl:value-of select="$lang1" />)</th>
              <th>Title (<xsl:value-of select="$lang1" />)</th>
              <th>Abbreviations (<xsl:value-of select="$lang1" />)</th>
              <th>Book (<xsl:value-of select="$lang2" />)</th>
              <th>Title (<xsl:value-of select="$lang2" />)</th>
              <th>Abbreviations (<xsl:value-of select="$lang2" />)</th>
            </tr>
          </thead>
          <tr>
            <xsl:apply-templates select="nls:nls/nls:book" />
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="nls:book">
    <tr>
      <xsl:apply-templates select="nls:localization" />
    </tr>
  </xsl:template>

  <xsl:template match="nls:book/nls:localization[@lang=$lang1]">
    <td><xsl:value-of select="@name" /></td>
    <td><xsl:value-of select="@title" /></td>
    <td><xsl:value-of select="@abbreviations" /></td>
  </xsl:template>

  <xsl:template match="nls:book/nls:localization[@lang=$lang2]">
    <td><xsl:value-of select="@name" /></td>
    <td><xsl:value-of select="@title" /></td>
    <td><xsl:value-of select="@abbreviations" /></td>
  </xsl:template>

</xsl:stylesheet>