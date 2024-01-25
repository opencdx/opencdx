
// ==============================|| ELEMENT REFERENCE HOOKS ||============================== //

const Dependency = () => {
   

    const openInNewTab = (url) => {
        const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
        if (newWindow) newWindow.opener = null;
    };

    return <div>{ openInNewTab('/dependency-check-report.html') }</div>;
};
export default Dependency;
