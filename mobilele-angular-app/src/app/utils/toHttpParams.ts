export function toHttpParams(obj: Record<string, any>): Record<string, string> {
  const params: Record<string, string> = {};

  Object.entries(obj).forEach(([key, value]) => {
    if (value != null && value !== '') {
      params[key] = value.toString();
    }
  });

  return params;
}
