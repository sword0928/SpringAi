package ${package.Entity};

<#list table.importPackages as pkg>
    import ${pkg};
</#list>

<#if entityLombokModel>
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
</#if>
@TableName("${table.name}")
public class ${entity} {

<#list table.fields as field>

    <#if field.comment??>
    /**
    * ${field.comment}
    */
    </#if>
    <#if field.keyFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
    <#else>
    @TableField("${field.name}")
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
}
