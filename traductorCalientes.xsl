<?xml version="1.0" encoding = "UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/cafe_order">
<cafe_order>
    <xsl:copy-of select="//order_id"/>
    <xsl:copy-of select="//drink"/>
    <sql>
        SELECT count(1)
        FROM bebidas_calientes
        WHERE bebidas_calientes.nombre = '<xsl:value-of select="//name"/>';
    </sql>
</cafe_order>
</xsl:template>
</xsl:stylesheet>