import React, { forwardRef } from 'react';
import useLocalStorage from '../../utils/useLocalStorage';

const QuestionsList = forwardRef((props, ref) => {
    const [files] = useLocalStorage('anf-form');
    const handleCopyToClipboard = (id) => {
        const textField = document.createElement('textarea');
        textField.innerText = `document.getElementById(${id}).value`;
        document.body.appendChild(textField);
        textField.select();
        document.execCommand('copy');
        textField.remove();
    };
    return (
        <div ref={ref} {...props}>
            <h2>List</h2>
            <p style={{ color: 'red' }}>
                This will be displayed in each ANF Staement above the tabs. <br /> This will allow users to copy the document id values to
                cliapboard and past into the ANF Statement fields.
            </p>
            <table>
                <thead>
                    <tr>
                        <th>Question Text</th>
                        <th>Question Link ID</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {files?.item.map((item, index) => (
                        <tr key={index}>
                            <td>{item.text}</td>
                            <td>document.getElementById({item.linkId}).value</td>
                            <td>
                                <button onClick={() => handleCopyToClipboard(item.linkId)}>Copy to Clipboard</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
});

export default QuestionsList;
