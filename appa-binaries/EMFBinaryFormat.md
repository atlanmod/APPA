
# EMF Binary Format

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/                    /
|       Header       | From 9 to 13 bytes
/                    /
+--+--+--+--+--+--+--+
/                    /
|      EObjects      | 
/                    /
+--+--+--+--+--+--+--+
```


## Header
    
    - Signature (8 bytes)
    - Version (1 byte)
    - Style (Optional)

```txt
0  1  2  3  4  5  6  7
+--+--+--+--+--+--+--+
/                    /
|     Signature      | 8 bytes
/                    /
+--+--+--+--+--+--+--+
|      Version       | 1 byte
+--+--+--+--+--+--+--+
/                    /
|       Style        | 8 bytes
/                    /
+--+--+--+--+--+--+--+
```

## Signature

- 8 bytes : chars {'\211','e','m','f','\n','\r','\032','\n'};

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
- 4 bytes 
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

## EObjects 

- Size : Compressed Int (1, 2, 3 or 4 bytes)
- Contents (in the first level, all resource's root elements {Check.CONTAINER})
  - EObject ID : Compressed Int
  - EClass ID: 
    - Package ID : Compressed Int 
    - EClass ID : Compressed Int
  - Structural Feature DATA
 
 
    0  1  2  3  4  5  6  7
    +--+--+--+--+--+--+--+
    |      EObject ID    | Compressed Int
    +--+--+--+--+--+--+--+
    |      EClass ID     | Compressed Int
    +--+--+--+--+--+--+--+
    |      EPackage ID   | Compressed Int
    +--+--+--+--+--+--+--+
    |                    |
    /                    /
    / Structural Feature /
    |        DATA        |
    +--+--+--+--+--+--+--+
    |          0         | Compressed Int
    +--+--+--+--+--+--+--+        


## Structural Features

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

## Feature Value

### EAttribute (monovalued)

- Boolean : 1 byte
- Byte       : 1 byte
- Char      : 1 byte
- Double  : 8 bytes
- Float      : 4 bytes
- Int          : 4 bytes
- Short     : 2 bytes
- Long      : 8 bytes

#### String

    0  1  2  3  4  5  6  7
    +--+--+--+--+--+--+--+
    /    String Size     / Compressed Int
    +--+--+--+--+--+--+--+
    |                    |
    /       Chars        /
    /                    /
    |                    |
    +--+--+--+--+--+--+--+

####  FEATURE_MAP;

    0  1  2  3  4  5  6  7
    +--+--+--+--+--+--+--+
    /   FeatureMap Size  / Compressed Int
    +--+--+--+--+--+--+--+
    |                    |
    /      Entries       /
    /                    /
    |                    |
    +--+--+--+--+--+--+--+

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

		Object value = entry.getValue();
		switch (eStructuralFeatureData.kind) {
		case EOBJECT:
		case EOBJECT_LIST:
		case EOBJECT_CONTAINMENT:
		case EOBJECT_CONTAINMENT_LIST: {
			saveEObject((InternalEObject) value, Check.NOTHING);
			break;
		}
		case EOBJECT_CONTAINMENT_PROXY_RESOLVING:
		case EOBJECT_CONTAINMENT_LIST_PROXY_RESOLVING: {
			saveEObject((InternalEObject) value, Check.DIRECT_RESOURCE);
			break;
		}
		case EOBJECT_PROXY_RESOLVING:
		case EOBJECT_LIST_PROXY_RESOLVING: {
			saveEObject((InternalEObject) value, Check.RESOURCE);
			break;
		}
		case BOOLEAN: {
			writeBoolean((Boolean) value);
			break;
		}
		case BYTE: {
			writeByte((Byte) value);
			break;
		}
		case CHAR: {
			writeChar((Character) value);
			break;
		}
		case DOUBLE: {
			writeDouble((Double) value);
			break;
		}
		case FLOAT: {
			writeFloat((Float) value);
			break;
		}
		case INT: {
			writeInt((Integer) value);
			break;
		}
		case LONG: {
			writeLong((Long) value);
			break;
		}
		case SHORT: {
			writeShort((Short) value);
			break;
		}
		case STRING: {
			writeSegmentedString((String) value);
			break;
		}
		case DATE: {
			if (eStructuralFeatureData.dataConverter != null) {
				eStructuralFeatureData.dataConverter.write(this, value);
			} else if ((style & STYLE_BINARY_DATE) != 0) {
				writeDate((Date) value);
			} else {
				writeSegmentedString(
						eStructuralFeatureData.eFactory.convertToString(eStructuralFeatureData.eDataType, value));
			}
			break;
		}
		case ENUMERATOR: {
			if (eStructuralFeatureData.dataConverter != null) {
				eStructuralFeatureData.dataConverter.write(this, value);
			} else if ((style & STYLE_BINARY_ENUMERATOR) != 0) {
				writeInt(((Enumerator) value).getValue());
			} else {
				writeString(eStructuralFeatureData.eFactory.convertToString(eStructuralFeatureData.eDataType, value));
			}
			break;
		}
		case DATA:
		case DATA_LIST: {
			if (eStructuralFeatureData.dataConverter != null) {
				eStructuralFeatureData.dataConverter.write(this, value);
			} else {
				String literal = eStructuralFeatureData.eFactory.convertToString(eStructuralFeatureData.eDataType,
						value);
				writeSegmentedString(literal);
			}
			break;
		}
		default: {
			throw new IOException("Unhandled case " + eStructuralFeatureData.kind);
		}
		}



#### Date

- Objects (if data converter)
- String (if not style binary date)
- Long (if style binary date)
				
#### DATA_LIST;

Attributes with a true `isMany()`

    0  1  2  3  4  5  6  7
    +--+--+--+--+--+--+--+
    /     List Size      / Compressed Int
    +--+--+--+--+--+--+--+
    |                    |
    /     Strings        / Values as strings (if no data converter)
    /        or          / OR
    |     Objects        | Values as objects (if data converter)
    +--+--+--+--+--+--+--+

Note: data converter seems to be null for primitive types

#### ENUMERATOR

- Three Different serialization possibilities:
  1. With a Data Converter
  2. If flag `STYLE_BINARY_ENUMERATOR` is set, uses an Integer (4 bytes)
  3. Otherwise: Use `EFactory::convertToString()` for DataType


#### DATA

    +--+--+--+--+--+--+--+
    |                    |
    /     Strings        / Values as strings (if no data converter)
    /        or          / OR
    |     Objects        | Values as objects (if data converter)
    +--+--+--+--+--+--+--+

### EReferences

####  isContainment() AND isResolveProxies() AND  isMany()

EOBJECT_CONTAINMENT_LIST_PROXY_RESOLVING;

#### isContainment() AND isResolveProxies() AND NOT  isMany()

EOBJECT_CONTAINMENT_PROXY_RESOLVING;

#### isContainment() AND NOT  isResolveProxies() AND  isMany()

EOBJECT_CONTAINMENT_LIST;

#### NOT isContainment() AND isContainer() AND  isResolveProxies() 

EOBJECT_CONTAINER_PROXY_RESOLVING;

#### NOT isContainment() AND isContainer() AND NOT isResolveProxies()

EOBJECT_CONTAINER

#### NOT isContainment()  AND NOT isContainer() AND isResolveProxies()

EOBJECT_LIST_PROXY_RESOLVING;

#### NOT isContainment()  AND NOT isContainer() AND NOT isResolveProxies() AND isMany()

EOBJECT_LIST;

#### NOT isContainment()  AND NOT isContainer() AND NOT isResolveProxies() AND isMany()

EOBJECT



## Conventions

### Compressed Int:
  
  - Integer with 1, 2, 3 or 4 bytes
  
  ### Null values
  
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
