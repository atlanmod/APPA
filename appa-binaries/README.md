



## Header 

- Version
- Option Flags
- Header should specify identifiers size (number of buffer)
- Number of EObjects.

## Ideas

Use IDs also for proxies.

+---+
| EClass id

```txt
+--+--+--+--+--+--+--+
|        Header      |
+--+--+--+--+--+--+--+
|         URIs       |
+--+--+--+--+--+--+--+
|       EPackages    |
+--+--+--+--+--+--+--+
|       EObjects     |
+--+--+--+--+--+--+--+
|     EReferences    |
+--+--+--+--+--+--+--+
|      EAttributes   |
+--+--+--+--+--+--+--+
```
