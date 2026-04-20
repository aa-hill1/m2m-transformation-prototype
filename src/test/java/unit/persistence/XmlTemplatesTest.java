package unit.persistence;

import org.example.persistence.XmlTemplates;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to ensure that XmlTemplates correctly combines icon data (from resources/icons), with component templates.
 */
public class XmlTemplatesTest {

    @Test
    public void testIconDataWithContext() {
        String[] postconditionTemplate =
                {
                        "\n\t<mxCell id=\"",
                        "\" parent=\"",
                        "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                                "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                                "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                        "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                        "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                                "\n\t</mxCell>"
                };
        String[] generatedOutput = XmlTemplates.getPostcondition();
        assertEquals(postconditionTemplate[0], generatedOutput[0]);
        assertEquals(postconditionTemplate[1], generatedOutput[1]);
        assertEquals(postconditionTemplate[4], generatedOutput[3]);
        try {
            Properties iconProperties = new Properties();
            iconProperties.load(new FileInputStream(
                    XmlTemplates.class.getClassLoader().getResource("icons/postcondition.properties").getPath()));
            String iconData = iconProperties.getProperty("img");
            assertEquals((postconditionTemplate[2]+iconData+postconditionTemplate[3]), generatedOutput[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIconDataWithComponent() {
        String[] stmRefTemplate =
                {
                        "\n\t<mxCell id=\"",
                        "\" parent=\"",
                        "\" style=\"rounded=0;whiteSpace=wrap;html=1;dashed=1;container=1;collapsible=0;" +
                                "fillColor=#B8B8B8;fontStyle=2;recursiveResize=0;\" value=\"&lt;img src=&quot;",
                        "&quot; width=&quot;16&quot; height=&quot;15&quot;&gt;ref ",
                        "\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"120\" x=\"",
                        "\" y=\"",
                        "\" as=\"geometry\" />\n\t</mxCell>"
                };
        String[] generatedOutput = XmlTemplates.getStm(false);
        assertEquals(stmRefTemplate[0], generatedOutput[0]);
        assertEquals(stmRefTemplate[1], generatedOutput[1]);
        assertEquals(stmRefTemplate[4], generatedOutput[3]);
        assertEquals(stmRefTemplate[5], generatedOutput[4]);
        try {
            Properties iconProperties = new Properties();
            iconProperties.load(new FileInputStream(
                    XmlTemplates.class.getClassLoader().getResource("icons/stmRef.properties").getPath()));
            String iconData = iconProperties.getProperty("img");
            assertEquals((stmRefTemplate[2]+iconData+stmRefTemplate[3]), generatedOutput[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
