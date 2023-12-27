import React from 'react';
import { diffChars } from 'diff';

const FileComparison = () => {
  const oldJson = `{
  "key1": "value1",
  "key2": "value2",
  "key4": "value4"
}`;

  const newJson = `{
  "key1": "updatedValue1",
  "key2": "value2",
  "key3": "value3"
}`;

  const differences = diffChars(oldJson, newJson);

  return (
    <div>
      {differences.map((part, index) => (
        <span
          key={index}
          style={
            part.added
              ? { background: 'lightgreen' }
              : part.removed
              ? { background: 'lightcoral' }
              : null
          }
        >
          {part.value.split('\n').map((line, lineIndex) => (
            <React.Fragment key={lineIndex}>
              {lineIndex > 0 && <br />} {line}
            </React.Fragment>
          ))}
        </span>
      ))}
    </div>
  );
};

export default FileComparison;
