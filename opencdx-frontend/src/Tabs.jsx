import React, { useEffect, useState } from 'react';
// import exampleHTML from './doc/javadoc/index.html';
// import exampleHTML from './example.html';

const Tabs = () => {
    const [activeTab, setActiveTab] = useState(1);
  //  const [htmlContent, setHtmlContent] = useState('');

    const handleTabClick = (tabNumber) => {
        setActiveTab(tabNumber);
    };
    // useEffect(() => {
    //     const fetchHTMLFile = async () => {
    //       try {
    //         const response = await fetch('doc/javadoc/index.html');
    //         const data = await response.text();
    //         setHtmlContent(data);
    //       } catch (error) {
    //         console.error('Error loading HTML file:', error);
    //       }
    //     };
    
    //     fetchHTMLFile();
    //   }, []);
    const IframeComponent = () => {
        // Replace "https://example.com" with the actual URL you want to embed
        const iframeSrc = "http://localhost:8222/";

        return (
            <div>


                <iframe
                    src={iframeSrc}
                  
                    height="600"
                    frameBorder="0"
                    allowFullScreen
                    title="Embedded Website"
                />
            </div>
        );
    }
    const IframeComponent2 = () => {
        // Replace "https://example.com" with the actual URL you want to embed
        const iframeSrc = "https://localhost:8861/admin/wallboard";

        return (
            <div>


                <iframe
                    src={iframeSrc}
                    height="600"

                    frameBorder="0"
                    allowFullScreen
                    title="Embedded Website"
                />
            </div>
        );
    }
    const IframeComponent3 = () => {
        // Replace "https://example.com" with the actual URL you want to embed
        const iframeSrc = "https://localhost:8761/";

        return (
            <div>


                <iframe
                    src={iframeSrc}
                    height="600"

                    frameBorder="0"
                    allowFullScreen
                    title="Embedded Website"
                />
            </div>
        );
    }
    return (
        <div>
            <div className="tab-buttons">
                <button onClick={() => handleTabClick(1)} className={activeTab === 1 ? 'active' : ''}>
                    NATS
                </button>
                <button onClick={() => handleTabClick(2)} className={activeTab === 2 ? 'active' : ''}>
                    Admin
                </button>
                <button onClick={() => handleTabClick(3)} className={activeTab === 3 ? 'active' : ''}>
                    Discovery
                </button> <button onClick={() => handleTabClick(4)} className={activeTab === 4 ? 'active' : ''}>
                    JavaDoc
                </button> <button onClick={() => handleTabClick(5)} className={activeTab === 5 ? 'active' : ''}>
                    ProtoDoc
                </button>
            </div>

            <div className="tab-content">
                {activeTab === 1 && <IframeComponent />
}
                {activeTab === 2 && <IframeComponent2/>}
                {activeTab === 3 && <IframeComponent3 />}     
                 {activeTab === 4 && 
                    <div> <link rel="apple-touch-icon" href="%PUBLIC_URL%/protodoc/index.html" >dd</link></div>

                // <div dangerouslySetInnerHTML={{ __html: htmlContent }} />
                 }     
 
            </div>
        </div>
    );
};

export default Tabs;
