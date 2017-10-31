package com.example.bar.util

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName

class RestDocHelper {
    static List<FieldDescriptor> array(List<FieldDescriptor> fields, String description) {

        List<FieldDescriptor> newFields = fields.collect {
            field -> fieldWithPath("[]." + field.getPath()).description(field.getDescription())
        }

        return [fieldWithPath("[]").description(description)] + newFields
    }


    static List<ParameterDescriptor> withPagingQuery(ParameterDescriptor parameterDescriptor) {
        return withPagingQuery([parameterDescriptor]);
    }

    static List<ParameterDescriptor> withPagingQuery(List<ParameterDescriptor> parameterDescriptors) {
        return parameterDescriptors + pagingQueryDescriptors()
    }

    static List<ParameterDescriptor> pagingQueryDescriptors() {
        return [parameterWithName("page").description("Number of the page"),
                parameterWithName("size").description("Number of items in the page"),
                parameterWithName("sort")
                        .description("Property to do sort. Descending order can be specified next to property name, delimited by comma: `firstName,desc`")
                        .optional()]
    }

    static List<FieldDescriptor> inPagingBody(List<FieldDescriptor> fieldDescriptors, String description) {
        List<FieldDescriptor> newFields = fieldDescriptors.collect { fd -> arrayIn("content", fd) }

        List<FieldDescriptor> pageFieldDescriptors = [
                fieldWithPath("content").description("Content"),
                fieldWithPath("content.[]").description(description),
                fieldWithPath("totalElements").description("Number of total elements"),
                fieldWithPath("totalPages").description("Number of total pages"),
                fieldWithPath("size").description("Size of the page"),
                fieldWithPath("number").description("The number of the current page. Is always non-negative."),
                fieldWithPath("sort").ignored(),
                fieldWithPath("first").description("Whether the current page is the first one."),
                fieldWithPath("last").description("Whether the current page is the last one."),
                fieldWithPath("numberOfElements").description("Number of elements currently on this page.")]

        return newFields + pageFieldDescriptors
    }

    private static FieldDescriptor arrayIn(String prefix, FieldDescriptor field) {
        return fieldWithPath(prefix + ".[]." + field.getPath()).description(field.getDescription());
    }

    static List<FieldDescriptor> toFieldDescriptors(Map<String, String> fieldNameToDescription) {
        fieldNameToDescription.collect { toFieldDescriptor(it) }
    }

    static FieldDescriptor toFieldDescriptor(Map.Entry<String, String> fieldNameToDescription) {
        fieldWithPath(fieldNameToDescription.key).description(fieldNameToDescription.value)
    }

    static List<ParameterDescriptor> toParameterDescriptors(Map<String, String> parameterNameToDescription) {
        parameterNameToDescription.collect { parameterWithName(it.key).description(it.value) }
    }
}
