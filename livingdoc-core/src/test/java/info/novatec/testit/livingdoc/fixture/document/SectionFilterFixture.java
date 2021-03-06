package info.novatec.testit.livingdoc.fixture.document;

import info.novatec.testit.livingdoc.Example;
import info.novatec.testit.livingdoc.document.SectionsTableFilter;
import info.novatec.testit.livingdoc.reflect.annotation.FixtureClass;
import info.novatec.testit.livingdoc.util.Tables;


@FixtureClass
public class SectionFilterFixture {
    public String documentContent;
    public String specifiedSectionsToFilter;

    public String interpretedElements() {
        SectionsTableFilter ctf = new SectionsTableFilter();

        if ( ! "none".equals(specifiedSectionsToFilter)) {
            ctf.includeSections(specifiedSectionsToFilter.split(","));
        }

        Example tableToFilter = Tables.parse(documentContent);
        StringBuilder ret = new StringBuilder();
        boolean none = true;

        while (tableToFilter != null) {
            if ( ! ctf.canFilter(tableToFilter)) {
                ret.append(tableToFilter.firstChild().toString());
                tableToFilter = tableToFilter.nextSibling();
                none = false;
            } else {
                tableToFilter = ctf.filter(tableToFilter);
            }
        }

        if (none) {
            return "none";
        }
        return ret.toString();
    }
}
