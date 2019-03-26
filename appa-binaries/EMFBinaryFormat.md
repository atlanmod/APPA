
# EMF Binary Format

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/                    /
|       Header       | From 9 to 13 buffer
/                    /
+--+--+--+--+--+--+--+
/                    /
|      EObjects      | 
/                    /
+--+--+--+--+--+--+--+
```


## Header
    
    - Signature (8 buffer)
    - Version (1 byte)
    - Style (Optional)

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/                    /
|     Signature      | 8 buffer
/                    /
+--+--+--+--+--+--+--+
|      Version       | 1 byte
+--+--+--+--+--+--+--+
/                    /
|       Style        | 8 buffer
/                    /
+--+--+--+--+--+--+--+
```

## Signature

- 8 buffer : chars {'\211','e','m','f','\n','\r','\032','\n'};

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|       '\211'       |
+--+--+--+--+--+--+--+
|         'e'        | 
+--+--+--+--+--+--+--+
|         'm'        | 
+--+--+--+--+--+--+--+
|         'f'        | 
+--+--+--+--+--+--+--+
|        '\n'        | 
+--+--+--+--+--+--+--+
|         'r'        | 
+--+--+--+--+--+--+--+
|       '\032'       | 
+--+--+--+--+--+--+--+
|        '\n'        | 
+--+--+--+--+--+--+--+
```


## Version

Version is an enumeration ( `enum BinaryIO.Version`), with two possible literals (`VERSION_1_0` and `VERSION_1_1`). 

- 1 byte: the version ordinal (0 or 1). 

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|                    | 1Byte
+--+--+--+--+--+--+--+
```


## Style

- Optional, only if version > 1.0 (ordinal > 0)
- 4 buffer 
```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|       Unused       |
+--+--+--+--+--+--+--+
|       Unused       | 
+--+--+--+--+--+--+--+
|       Unused       | 
+--+--+--+--+--+--+--+
|     | a| b| c| d| e| 
+--+--+--+--+--+--+--+
```

a : STYLE_DATA_CONVERTER        (bit 27) // Uses segmented String
b : STYLE_BINARY_ENUMERATOR (bit 28)
c : STYLE_PROXY_ATTRIBUTES      (bit 29)
d : BINARY_DATE                               (bit 30)
e : BINARY_FLOATING_POINT         (bit 31)


## EPackage

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|     EPackage ID    | Compressed Int
+--+--+--+--+--+--+--+
|                    |
/       NsURI        / String (Only the 1st time)
|                    |
+--+--+--+--+--+--+--+
|                    |
/        URI         / URI (Only the 1st time)
|                    |
+--+--+--+--+--+--+--+
```

- Package ID id Compressed Int
- NSURI : Segmented String
- URI : URI

## EClass

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|      EClass ID     | Compressed Int
+--+--+--+--+--+--+--+
|                    |
/     EClass Name    / String (Only the 1st time)
|                    |
+--+--+--+--+--+--+--+
```
## EObjects 

- Size : Compressed Int (1, 2, 3 or 4 buffer)
- Contents (in the first level, all resource's root elements {Check.CONTAINER})
  - EObject ID : Compressed Int
  - EClass ID: 
    - Package ID : Compressed Int 
    - EClass ID : Compressed Int
  - Structural Feature DATA
 
 ```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|      EObject ID    | Compressed Int
+--+--+--+--+--+--+--+
|       EClass       | 
+--+--+--+--+--+--+--+
|      EPackage      | 
+--+--+--+--+--+--+--+
|                    |
/                    /
/ Structural Feature /
|        DATA        |
+--+--+--+--+--+--+--+
|          1         | Compressed Int
+--+--+--+--+--+--+--+        
```

## Structural Features

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/  Feature ID + 1    / Compressed Int
+--+--+--+--+--+--+--+
/    Feature NAME    / String (Only for the 1st time the feature is written)
+--+--+--+--+--+--+--+
|   Data Converter   | Boolean (Only for the 1st time the feature is written)
+--+--+--+--+--+--+--+
/   Feature Value    /
+--+--+--+--+--+--+--+
```

## Feature Value

### EAttribute (monovalued)

- Boolean : 1 byte
- Byte       : 1 byte
- Char      : 1 byte
- Double  : 8 buffer
- Float      : 4 buffer
- Int          : 4 buffer
- Short     : 2 buffer
- Long      : 8 buffer

#### String

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/    String Size     / Compressed Int
+--+--+--+--+--+--+--+
|                    |
/       Chars        /
/                    /
|                    |
+--+--+--+--+--+--+--+
```

####  FEATURE_MAP;

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/   FeatureMap Size  / Compressed Int
+--+--+--+--+--+--+--+
|                    |
/      Entries       /
/                    /
|                    |
+--+--+--+--+--+--+--+
```

##### Feature Map Entry

```txt
0  1  2  3  4  5  6  7. // writeEStructuralFeature()
+--+--+--+--+--+--+--+
/      EObject ID    / Compressed Int
+--+--+--+--+--+--+--+
/      EClass ID     / Compressed Int
+--+--+--+--+--+--+--+
/      EPackage ID   / Compressed Int
+--+--+--+--+--+--+--+
/  Feature ID + 1    / Compressed Int
+--+--+--+--+--+--+--+
/    Feature NAME    / String (Only for the 1st time the feature is written)
+--+--+--+--+--+--+--+
|   Data Converter   | Boolean (Only for the 1st time the feature is written)
+--+--+--+--+--+--+--+
/   Feature Value    /
+--+--+--+--+--+--+--+
|                    |
/    Entry Value     /
/                    /
|                    |
+--+--+--+--+--+--+--+
```

- Entries: call `writeEStructuralFeature()`

#### Feature Map entry Value



#### Date

- Objects (if data converter)
- String (if not style binary date)
- Long (if style binary date)
				
#### DATA_LIST;

Attributes with a true `isMany()`

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/     List Size      / Compressed Int
+--+--+--+--+--+--+--+
|                    |
/     Strings        / Values as strings (if no data converter)
/        or          / OR
|     Objects        | Values as objects (if data converter)
+--+--+--+--+--+--+--+
```

Note: data converter seems to be null for primitive types

#### ENUMERATOR

- Three Different serialization possibilities:
  1. With a Data Converter
  2. If flag `STYLE_BINARY_ENUMERATOR` is set, uses an Integer (4 buffer)
  3. Otherwise: Use `EFactory::convertToString()` for DataType


#### DATA

```txt
+--+--+--+--+--+--+--+
|                    |
/     Strings        / Values as strings (if no data converter)
/        or          / OR
|     Objects        | Values as objects (if data converter)
+--+--+--+--+--+--+--+
```

### EReferences

####  isContainment() AND isResolveProxies() AND  isMany()

- EOBJECT_CONTAINMENT_LIST_PROXY_RESOLVING;

```java
InternalEList<? extends InternalEObject> internalEList = (InternalEList<? extends InternalEObject>) value;
			saveEObjects(internalEList, Check.DIRECT_RESOURCE);
```

#### NOT isContainment()  AND NOT isContainer() AND isResolveProxies()

EOBJECT_LIST_PROXY_RESOLVING;

```java
InternalEList<? extends InternalEObject> internalEList = (InternalEList<? extends InternalEObject>) value;
			saveEObjects(internalEList, Check.RESOURCE);
```


#### isContainment() AND isResolveProxies() AND NOT  isMany()

- EOBJECT_CONTAINMENT_PROXY_RESOLVING;

- `saveEObject((InternalEObject) value, Check.DIRECT_RESOURCE);`


#### NOT isContainment() AND isContainer() AND  isResolveProxies() 

- EOBJECT_CONTAINER_PROXY_RESOLVING;
- EOBJECT_PROXY_RESOLVING:
- `saveEObject((InternalEObject) value, Check.RESOURCE);`

 ```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|      EObject ID    | Compressed Int
+--+--+--+--+--+--+--+
|       EClass       | 
+--+--+--+--+--+--+--+
|      EPackage      | 
+--+--+--+--+--+--+--+
|        0           |
+--+--+--+--+--+--+--+
|                    |
/        URI         /
|                    |
+--+--+--+--+--+--+--+
```

#### 




## Multivalued Reference (Simple or Containment)

- NOT isContainment()  AND NOT isContainer() AND NOT isResolveProxies() AND isMany()
- isContainment() AND NOT  isResolveProxies() AND  isMany()
- EOBJECT_CONTAINMENT_LIST or EOBJECT_LIST;
- `saveEObjects(internalEList, Check.NOTHING)`

 ```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|        size        | Compressed Int
+--+--+--+--+--+--+--+
|   EObjects         |
(...)
```

## Single Reference (Simple or Containment)

- NOT isContainment()  AND NOT isContainer() AND NOT isResolveProxies() AND isMany()
- EOBJECT or EOBJECT_CONTAINMENT
- `saveEObject((InternalEObject) value, Check.NOTHING)`;

 ```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
|      EObject ID    | Compressed Int
+--+--+--+--+--+--+--+
|       EClass       | 
+--+--+--+--+--+--+--+
|      EPackage      | 
+--+--+--+--+--+--+--+
|                    |
/                    /
/ Structural Feature /
|        DATA        |
+--+--+--+--+--+--+--+
|          1         | Compressed Int
+--+--+--+--+--+--+--+
```

## Conventions

### Compressed Int:
  
Compressed Ints use the first two bits of a byte to specify the size (in buffer) of the written Integer.


- 1 Byte Integer:

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
| 0| 0|   Value      | Compressed Int
+--+--+--+--+--+--+--+
```

- 2 Bytes Integer

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
| 0| 1|   Value      | Compressed Int
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
```

+ 3 Bytes Integer

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
| 1| 0|   Value      | Compressed Int
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
```

- 4 Bytes Integer

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
| 1| 1|   Value      | Compressed Int
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
|        Value       |
+--+--+--+--+--+--+--+
```
  
  - Integer with 1, 2, 3 or 4 buffer
  
- All integers are incremented (+1) before written. 
- Null values are represented by the compressed Int "-1"

### Data converters 

- Hard to understand how it works
- Data convefrters are sublkcasses of `DataConverter`

```java
/**
 * Generally this abstract class is extended as a stateless singleton returned
 * by a generated factory that implements the {@link DataConverter.Factory
 * factory} interface. The default implementation of
 * {@link EFactoryImpl#create(EDataType)} returns <code>null</code>.
 * 
 * @since 2.9
 */
public abstract class DataConverter<T> {
```

## Segmented String

ID              : Compressed Int
Segment Count   : Compressed Int