package org.example.persistence;

import org.example.app.transformModel.context.ContextType;
import org.example.app.transformModel.stm.JunctionType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class XmlTemplates {
    private static Properties iconProperties = new Properties();
    private static final String[] BASE =
            {
                    "<mxfile host=\"app.diagrams.net\" agent=\"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:148.0) " +
                            "Gecko/20100101 Firefox/148.0\" version=\"29.6.6\">" +
                            "\n<diagram name=\"Page-1\" id=\"diagramID\">" +
                            "\n<mxGraphModel dx=\"500\" dy=\"500\" grid=\"1\" gridSize=\"10\" " +
                            "guides=\"1\" tooltips=\"1\" connect=\"1\" arrows=\"1\" " +
                            "fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"3300\" " +
                            "pageHeight=\"4681\" math=\"0\" shadow=\"0\">" +
                            "\n<root>" +
                            "\n\t<mxCell id=\"0\" />" +
                            "\n\t<mxCell id=\"1\" parent=\"0\" />",
                    // Components
                    "\n</root>\n</mxGraphModel>\n</diagram>\n</mxfile>"
            };
    private static final String[] PACKAGE =
            {
                    "\n\t<mxCell id=\"",
                    // + ID +
                    "\" parent=\"1\" style=\"html=1;whiteSpace=wrap;fontStyle=5\" value=\"package ",
                    // + name +
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"30\" width=\"110\" x=\"",
                    // + x +
                    "\" y=\"",
                    // + y +
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] TYPE_DEC =
            {
                    "\n\t<mxCell id=\"",
                    // + ID +
                    "\" parent=\"1\" style=\"html=1;whiteSpace=wrap;\" value=\"",
                    // + name +
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"40\" width=\"110\" x=\"",
                    // + x +
                    "\" y=\"",
                    // + y +
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] IMPORTS =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;b&gt;imports&lt;/b&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"52\" " +
                            "width=\"140 x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] ENUM =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"52\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] RECORD =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"52\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] FUNCTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;14&quot; height=&quot;14&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"52\" width=\"230\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] INTERFACE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;span&gt;",
                    // name
                    "&lt;/span&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"52\" width=\"142.5\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] MODULE =
            {
                    "\n\t<mxCell id=\"",
                    // ID-0
                    "\" parent=\"1\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"142.5\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // ID-0
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;\" " +
                            "value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"142.5\" y=\"26\" " +
                            "as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] OP_DEF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&lt;span&gt;&amp;nbsp;",
                    // name
                    "&lt;/span&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"142.5\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>",
                    // context
                    "\n\t<mxCell id=\"",
                    // StmBody ID
                    "\" parent=\"",
                    // ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;\" " +
                            "value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"142.5\" y=\"26\" " +
                            "as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] CONTROLLER_DEF =
            {
                    "\n\t<mxCell id=\"",
                    // ID-0
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;16&quot; height=&quot;16&quot;&gt;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"140\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>",
                    // context
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // ID-0
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;\" " +
                            "value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"140\" y=\"26\" " +
                            "as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] RP_DEF =
            {
                    "\n\t<mxCell id=\"",
                    // ID-0
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;16&quot; height=&quot;15&quot;&gt;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"157.5\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>",
                    // context
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // ID-0
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;" +
                            "strokeColor=none;\" value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" " +
                            "width=\"157.5\" y=\"26\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] STM_DEF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" " +
                            "value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;16&quot; height=&quot;16&quot;&gt;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"140\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>",
                    // context
                    "\n\t<mxCell id=\"",
                    // StmBody ID
                    "\" parent=\"",
                    // ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;" +
                            "\" value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"140\" y=\"26\" " +
                            "as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] STATE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;fontStyle=0;childLayout=stackLayout;horizontal=1;startSize=26;" +
                            "fillColor=default;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;" +
                            "collapsible=0;marginBottom=0;whiteSpace=wrap;html=1;verticalAlign=middle;\" value=\"",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"86\" width=\"140\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>",
                    // context
                    "\n\t<mxCell id=\"",
                    // StmBody ID
                    "\" parent=\"",
                    // ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;fillColor=none;container=1;collapsible=0;recursiveResize=0;\" " +
                            "value=\"\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"140\" y=\"26\" " +
                            "as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] OP_REF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;container=1;collapsible=0;fillColor=#B8B8B8;" +
                            "fontStyle=2;recursiveResize=0;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;ref ",
                    // referencedObject name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"40\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] CONTROLLER_REF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;container=1;collapsible=0;fillColor=#B8B8B8;recursiveResize=0;" +
                            "fontStyle=2\" value=\"&lt;img src=&quot;",
                    //img
                    "&quot; width=&quot;16&quot; height=&quot;16&quot;&gt;ref ",
                    // referencedObject name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] RP_REF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;container=1;collapsible=0;fillColor=#B8B8B8;recursiveResize=0;" +
                            "fontStyle=2;verticalAlign=middle;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;16&quot; height=&quot;16&quot;&gt;ref ",
                    // referencedObject name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] STM_REF =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"rounded=0;whiteSpace=wrap;html=1;dashed=1;container=1;collapsible=0;fillColor=#B8B8B8;fontStyle=2;recursiveResize=0;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;16&quot; height=&quot;15&quot;&gt;ref ",
                    // referencedObject name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"60\" width=\"120\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] FINAL_STATE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"ellipse;whiteSpace=wrap;html=1;aspect=fixed;" +
                            "points=[[0,0.5,0,0,0],[0.5,0,0,0,0],[0.5,1,0,0,0],[1,0.5,0,0,0]];\" " +
                            "value=\"&lt;b&gt;F&lt;/b&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"20\" " +
                            "width=\"20\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] JUNCTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#000000;" +
                            "points=[[0,0.5,0,0,0],[0.5,0,0,0,0],[0.5,1,0,0,0],[1,0.5,0,0,0]];\" value=\"\" " +
                            "vertex=\"1\">\n\t\t<mxGeometry height=\"20\" width=\"20\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] INITIAL_JUNCTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#000000;" +
                            "points=[[0.5,1,0,0,0]];\" value=\"&lt;b&gt;&lt;span style=&quot;" +
                            "color: light-dark(rgb(255, 255, 255), rgb(237, 237, 237));" +
                            "&quot;&gt;i&lt;/span&gt;&lt;/b&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"20\" " +
                            "width=\"20\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] PROB_JUNCTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"ellipse;whiteSpace=wrap;html=1;aspect=fixed;" +
                            "points=[[0,0.5,0,0,0],[0.5,0,0,0,0],[0.5,1,0,0,0],[1,0.5,0,0,0]];fontStyle=1\" " +
                            "value=\"P\" vertex=\"1\">\n\t\t<mxGeometry height=\"20\" width=\"20\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] TEXT_ROW =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] POSTCONDITION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] PRECONDITION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] EVENT =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] OP_SIG =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] CONSTANT =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;" +
                            "spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];" +
                            "portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;div&gt;&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "&lt;/div&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] VAR =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;div&gt;&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "&lt;/div&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] CLOCK =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;div&gt;&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "&lt;/div&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] REQ_INTERFACE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;div&gt;&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "&lt;/div&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] PROV_INTERFACE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;div&gt;&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "&lt;/div&gt;\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] DEF_INTERFACE =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;whiteSpace=wrap;html=1;\" value=\"&lt;img src=&quot;",
                    // img
                    "&quot; width=&quot;15&quot; height=&quot;15&quot;&gt;&amp;nbsp;",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"26\" width=\"142.5\" y=\"26\" as=\"geometry\" />" +
                            "\n\t</mxCell>"
            };
    private static final String[] EVENT_BOX =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" parent=\"",
                    // parent ID
                    "\" style=\"swimlane;startSize=0;strokeOpacity=100;collapsible=0;strokeWidth=2;align=left;direction=south;fontStyle=0;points=[[0.5,1,0,0,0]];\" value=\"",
                    // name
                    "\" vertex=\"1\">\n\t\t<mxGeometry height=\"20\" width=\"20\" x=\"",
                    // x
                    "\" y=\"",
                    // y
                    "\" as=\"geometry\" />\n\t</mxCell>"
            };
    private static final String[] CONNECTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" edge=\"1\" parent=\"",
                    // parent ID
                    "\" source=\"",
                    // src ID
                    "\" style=\"endArrow=openThin;html=1;rounded=0;strokeWidth=2;endFill=0;strokeColor=light-dark(#686868, #676767);exitX=0.5;exitY=1;exitDx=0;exitDy=0;exitPerimeter=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;entryPerimeter=0;\" target=\"",
                    // tgt ID
                    "\" value=\" \">\n\t\t<mxGeometry height=\"50\" relative=\"1\" width=\"50\" as=\"geometry\">\n\t\t\t<mxPoint x=\"160\" y=\"250\" as=\"sourcePoint\" />\n\t\t\t<mxPoint x=\"250\" y=\"250\" as=\"targetPoint\" />\n\t\t</mxGeometry>\n\t</mxCell>\n\t<mxCell id=\"",
                    // ID-0
                    "\" connectable=\"0\" parent=\"",
                    // ID
                    "\" style=\"edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontStyle=",
                    // FontStyle (for async)
                    "\" value=\"",
                    // label
                    "\" vertex=\"1\">\n\t\t<mxGeometry relative=\"1\" x=\"0.3241\" as=\"geometry\">\n\t\t\t<mxPoint x=\"-16\" y=\"-10\" as=\"offset\" />\n\t\t</mxGeometry>\n\t</mxCell>"
            };
    private static final String[] BIDI_CONNECTION =
            {
                    "\n\t<mxCell id=\"",
                    // ID
                    "\" edge=\"1\" parent=\"",
                    // parent ID
                    "\" source=\"",
                    // src ID
                    "\" style=\"endArrow=openThin;html=1;rounded=0;strokeWidth=2;endFill=0;strokeColor=light-dark(#686868, #676767);startArrow=openThin;startFill=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;entryPerimeter=0;exitX=0.5;exitY=1;exitDx=0;exitDy=0;exitPerimeter=0;\" target=\"",
                    // tgt ID
                    "\" value=\"\">\n\t\t<mxGeometry height=\"50\" relative=\"1\" width=\"50\" as=\"geometry\">\n\t\t\t<mxPoint x=\"280\" y=\"240\" as=\"sourcePoint\" />\n\t\t\t<mxPoint x=\"370\" y=\"240\" as=\"targetPoint\" />\n\t\t</mxGeometry>\n\t</mxCell>\n\t<mxCell id=\"",
                    // ID-0
                    "\" connectable=\"0\" parent=\"",
                    // ID
                    "\" style=\"edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];\" value=\"",
                    // label
                    "\" vertex=\"1\">\n\t\t<mxGeometry relative=\"1\" x=\"0.3241\" as=\"geometry\">\n\t\t\t<mxPoint x=\"-16\" y=\"-10\" as=\"offset\" />\n\t\t</mxGeometry>\n\t</mxCell>"
            };

    public static String getStartBase() {
        return BASE[0];
    }

    public static String getEndBase() {
        return BASE[1];
    }

    public static String[] getPackage() {
        return PACKAGE;
    }

    public static String[] getTypeDec() {
        return TYPE_DEC;
    }

    public static String[] getImports() {
        return IMPORTS;
    }

    public static String[] getInterface() {
        return INTERFACE;
    }

    public static String[] getEnum() {
        return combineIconData(ENUM, 1, "icons/enum.properties");
    }

    public static String[] getRecord() {
        return combineIconData(RECORD, 1, "./icons/record.properties");
    }

    public static String[] getFunction() {
        return combineIconData(FUNCTION, 1, "icons/function.properties");
    }

    public static String[] getModule() {
        return combineIconData(MODULE, 1, "icons/module.properties");
    }

    public static String[] getEventBox() {
        return EVENT_BOX;
    }

    public static String[] getConnection(boolean bidi) {
        return (bidi)? BIDI_CONNECTION : CONNECTION;
    }

    public static String[] getJunction(JunctionType type) {
        String[] returnData;
        switch (type) {
            case INITIAL -> returnData = INITIAL_JUNCTION;
            case PROB -> returnData = PROB_JUNCTION;
            default -> returnData = JUNCTION;
        }
        return returnData;
    }

    public static String[] getState(boolean reqFinal) {
        if (reqFinal) {
            return FINAL_STATE;
        }
        return STATE;
    }

    public static String[] getOperation(boolean def) {
        String path;
        String[] original;
        if (def) {
            path = "icons/opDefSig.properties";
            original = OP_DEF;
        } else {
            path = "icons/opRef.properties";
            original = OP_REF;
        }
        return combineIconData(original, 2, path);
    }

    public static String[] getController(boolean def) {
        String path;
        String[] original;
        if (def) {
            path = "icons/conDef.properties";
            original = CONTROLLER_DEF;
        } else {
            path = "icons/conRef.properties";
            original = CONTROLLER_REF;
        }
        return combineIconData(original, 2, path);
    }

    public static String[] getRp(boolean def) {
        String path;
        String[] original;
        if (def) {
            path = "icons/rpDef.properties";
            original = RP_DEF;
        } else {
            path = "icons/rpRef.properties";
            original = RP_REF;
        }
        return combineIconData(original, 2, path);
    }

    public static String[] getStm(boolean def) {
        String path;
        String[] original;
        if (def) {
            path = "icons/stmDef.properties";
            original = STM_DEF;
        } else {
            path = "icons/stmRef.properties";
            original = STM_REF;
        }
        return combineIconData(original, 2, path);
    }

    public static String[] getTextRow() {
        return TEXT_ROW;
    }

    public static String[] getPostcondition() {
        return combineIconData(POSTCONDITION, 2, "icons/postcondition.properties");
    }

    public static String[] getPrecondition() {
        return combineIconData(PRECONDITION, 2, "icons/precondition.properties");
    }

    public static String[] getEvent() {
        return combineIconData(EVENT, 2, "icons/event.properties");
    }

    public static String[] getOpSig() {
        return combineIconData(OP_SIG, 2, "icons/opDefSig.properties");
    }

    public static String[] getConstant() {
        return combineIconData(CONSTANT, 2, "icons/var.properties");
    }

    public static String[] getVar() {
        return combineIconData(VAR, 2, "icons/var.properties");
    }

    public static String[] getClock() {
        return combineIconData(CLOCK, 2, "icons/clock.properties");
    }

    public static String[] getInterfaceRef(ContextType type) {
        String[] original;
        String path = switch (type) {
            case R_INTERFACE -> {
                original = REQ_INTERFACE;
                yield "icons/reqInterface.properties";
            }
            case P_INTERFACE -> {
                original = PROV_INTERFACE;
                yield "icons/proInterface.properties";
            }
            case D_INTERFACE -> {
                original = DEF_INTERFACE;
                yield "icons/defInterface.properties";
            }
            default -> throw new RuntimeException("Error - cannot find interface template of type " + type);
        };
        return combineIconData(original, 2, path);
    }

    private static String[] combineIconData(String[] original, int indexToCombo, String path) {
        String[] dataToReturn = new String[original.length - 1];
        try {
            iconProperties.load(new FileInputStream(XmlTemplates.class.getClassLoader().getResource(path).getPath()));
            for (int i=0; i<original.length; i++) {
                if (i ==  indexToCombo) {
                    dataToReturn[i] = original[i] + iconProperties.getProperty("img") + original[i+1];
                    i++;
                } else if (i > indexToCombo) {
                    dataToReturn[i-1] = original[i];
                } else {
                    dataToReturn[i] = original[i];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataToReturn;
    }
}