# XML Parser Application

## Overview
The **XML Parser Application** is a custom Java-based tool for reading, manipulating, and querying XML-like files. The project is designed to provide practical experience in working with structured text files, object-oriented programming, and implementing basic XML/XPath functionalities **without using any external XML libraries**.

The application supports a simplified XML format and a subset of XPath-like queries, making it suitable for learning the internal workings of XML parsing and tree data structures.

---

## Features

### Core Functionalities
The application allows the following operations on XML documents:

1. **File Management**
   - Open and read XML files from the filesystem.
   - Save the document to the current file or a new file (`save` / `saveas`).
   - Close files and release resources.

2. **Element Management**
   - Automatically generate **unique IDs** for all elements.
     - Use existing IDs if they are unique.
     - Append a suffix if the ID is duplicated.
     - Generate a new ID if the element has no `id`.
   - Create new child elements.
   - Delete element attributes.
   - Set or update attribute values.

3. **Viewing and Navigation**
   - Print the XML structure in a readable, properly indented format.
   - Access element text content.
   - List attributes of child elements.
   - Access n-th child of an element.

4. **XPath-like Queries**
   - Perform simplified XPath 2.0 queries on elements:
     - `/` operator: access child nodes (e.g., `person/address`)
     - `[]` operator: access elements by index (e.g., `person/address[0]`)
     - `@` operator: access attributes (e.g., `person(@id)`)
     - `=` operator: filter elements by attribute or text value (e.g., `person(address="USA")/name`)

### Additional Functionalities
- Formatted, “pretty” XML output with proper indentation.
- Handles duplicate IDs and missing IDs automatically.
- Interactive command-line interface with clear commands and arguments.
- Basic input validation for commands and file handling.

---

## Supported Commands

| Command          | Description |
|-----------------|------------|
| `open <file>`    | Opens an XML file. Only `.xml` files are accepted. |
| `save`           | Saves the current XML document to the current file. |
| `saveas <file>`  | Saves the document to a new file (must end with `.xml`). |
| `close`          | Closes the current file and releases resources. |
| `help`           | Displays available commands. |
| `exit`           | Exits the application. |
| `print`          | Prints the XML structure with indentation. |
| `select <id> <key>` | Displays the value of a specific attribute for an element. |
| `set <id> <key> <value>` | Sets or updates the value of a specific attribute. |
| `children <id>`  | Lists all attributes of child elements of the specified element. |
| `child <id> <n>` | Accesses the n-th child of a specified element. |
| `text <id>`      | Displays the text content of a specified element. |
| `delete <id> <key>` | Deletes an attribute of the element. |
| `newchild <id>`  | Adds a new child element with an automatically generated ID. |
| `xpath <id> <XPath>` | Evaluates a simplified XPath expression on a specific element. |

---

## Implementation Details

### Data Structures
- **Tree-based structure:** The XML document is stored as a hierarchy of `XMLElement` objects, each representing an element with attributes, text, and child elements.
- **Element map:** A `HashMap` stores all elements by their IDs for fast access (`O(1)` lookup).

### Parsing Algorithm
- Reads the file line by line.
- Extracts tag names, attributes, and text content.
- Automatically handles duplicate and missing IDs.
- Constructs the hierarchical XML tree.

### Formatting
- Elements are printed with indentation based on their nesting level.
- Attributes and text content are properly aligned for readability.

### XPath Evaluation
- Supports a limited set of XPath-like operators (`/`, `[]`, `@`, `=`).
- Recursively evaluates queries starting from a given element.
- Returns lists of matching elements or attribute values.

---

## Example Usage

### Open and Print
```text
> open sample.xml
XML document opened successfully

> print
<people>
    <person id="0">
        <name>John Smith</name>
        <address>USA</address>
    </person>
    <person id="1">
        <name>Ivan Petrov</name>
        <address>Bulgaria</address>
    </person>
</people>
```
### XPath Query
```text
> xpath 0 person/address
[address elements of person 0]
```
### Set Attribute
```text
> set 0 name "John A. Smith
Attribute updated successfully
```
---
## Limitations
- This parser handles simplified XML-like files only; it does not fully comply with XML 1.0 specifications.
- Only a subset of XPath functionality is implemented.
- No support for external DTDs, namespaces (bonus feature), or advanced XML features.
---
## Bonus Features
- XML namespaces support (optional).
- Extended XPath axes: `ancestor`, `child`, `parent`, `descendant` (optional).
---
## Development Environment
- Java 17+
- No external XML libraries are used.
---
## How to Run
**1.** Clone the repository.
**2.** Compile the project.
**3.** Run the application.
