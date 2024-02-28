import { create } from 'zustand';

const useAnfFormStore = create((set) => {
  const files = JSON.parse(localStorage.getItem('anf-form'));  
  return {
    formData: files?.updated || {},
    setFormData: (data) => {
      set((state) => ({
        formData: { ...state.formData, ...data },
      }));
    },
    uploadData: files?.default ||{},
    setUploadData: (data) => {
      set((state) => ({
        uploadData: { ...state.uploadData, ...data },
      }));
    },
  };
});

export default useAnfFormStore;
export { useAnfFormStore };
